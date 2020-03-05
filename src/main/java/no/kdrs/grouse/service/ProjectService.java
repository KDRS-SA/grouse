package no.kdrs.grouse.service;

import no.kdrs.grouse.model.*;
import no.kdrs.grouse.persistence.*;
import no.kdrs.grouse.service.interfaces.IProjectRequirementService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
@Service
@Transactional
public class ProjectService
        extends GrouseService
        implements IProjectService {

    private EntityManager entityManager;
    private IProjectRequirementService projectRequirementService;
    private IProjectRepository projectRepository;
    private ITemplateRequirementRepository requirementRepository;
    private ITemplateFunctionalityRepository functionalityRepository;
    private IProjectRequirementRepository projectRequirementRepository;
    private IProjectFunctionalityRepository projectFunctionalityRepository;

    // Columns that it is possible to update via a PATCH request
    private ArrayList<String> allowableColumns =
            new ArrayList<String>(Arrays.asList("projectName",
                    "fileNameInternal", "ownedBy"));

    public ProjectService(
            EntityManager entityManager,
            IProjectRequirementService projectRequirementService,
            IProjectRepository projectRepository,
            ITemplateRequirementRepository requirementRepository,
            ITemplateFunctionalityRepository functionalityRepository,
            IProjectRequirementRepository projectRequirementRepository,
            IProjectFunctionalityRepository projectFunctionalityRepository) {
        this.entityManager = entityManager;
        this.projectRequirementService = projectRequirementService;
        this.projectRepository = projectRepository;
        this.requirementRepository = requirementRepository;
        this.functionalityRepository = functionalityRepository;
        this.projectRequirementRepository = projectRequirementRepository;
        this.projectFunctionalityRepository = projectFunctionalityRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProjectRequirement> findByProjectIdOrderByProjectName(
            Long projectId, String functionalityNumber) {
        String queryString =
                "select p from ProjectRequirement as p where " +
                        "p.referenceProject.projectId = :projectId " +
                        "AND p.referenceFunctionality.functionalityNumber = " +
                        ":functionalityNumber";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("projectId", projectId);
        query.setParameter("functionalityNumber", functionalityNumber);
        return query.getResultList();
    }

    /**
     * findFunctionalityForProject
     * <p>
     * Get a list of ProjectFunctionality for a give Project
     *
     * @param projectId Id of the project to retrieve ProjectFunctionality
     * @return list of ProjectFunctionality
     */
    @Override
    public List<ProjectFunctionality> findFunctionalityForProjectByType(
            Long projectId, String type) {
        return projectFunctionalityRepository.
                findByReferenceProjectAndTypeAndShowMe(
                        getProjectOrThrow(projectId), type, true);
    }


    @Override
    public Page<Project> findAll(Pageable page) {
        return projectRepository.findAll(page);
    }

    @Override
    public Project findById(@NotNull Long id) {
        return getProjectOrThrow(id);
    }

    /**
     * Create a new project.
     * <p>
     * The following steps are performed:
     * 1. Retrieve GrouseUser object from loggedin user and associate with project
     * 2. Copy all SRequirement objects and create ProjectRequirement objects
     * 3. Copy all TemplateFunctionality objects and create ProjectFunctionality objects
     * <p>
     * This is done as each project needs their own copy to work on.
     * Requirements are needed as within the project the used can change the
     * text, add or remove ProjectRequirements
     * ProjectFunctionality are needed to save state of the progress. For each
     * step that is processed a progress value is set. This is useful to be
     * able to reload the project but also eases the GUI side of things.
     * <p>
     * State is stored in the database, not the GUI
     *
     * @param project The project object to create
     * @return The persisted object after it was persisted with associated data
     */
    @Override
    public Project createProject(Project project) {
        String ownedBy = getUser();
        project.setDocumentCreated(false);

        project.setFileName(project.getProjectName());
        project.setOwnedBy(ownedBy);
        projectRepository.save(project);

        ArrayList<TemplateFunctionality> functionalities = (ArrayList<TemplateFunctionality>)
                functionalityRepository.findAll();
        //findAllByOrderById();

        HashMap<String, ProjectFunctionality> parents = new HashMap<>();

        for (TemplateFunctionality templateFunctionality : functionalities) {

            ProjectFunctionality existingProjectFunctionality
                    = parents.get(templateFunctionality.getFunctionalityNumber());

            if (existingProjectFunctionality != null) {
                continue;
            }

            ProjectFunctionality projectFunctionality =
                    new ProjectFunctionality();

            projectFunctionality.setFunctionalityNumber(
                    templateFunctionality.getFunctionalityNumber());
            projectFunctionality.setTitle(
                    templateFunctionality.getTitle());
            projectFunctionality.setConsequence(
                    templateFunctionality.getConsequence());
            projectFunctionality.setDescription(
                    templateFunctionality.getDescription());
            projectFunctionality.setExplanation(
                    templateFunctionality.getExplanation());
            projectFunctionality.setType(
                    templateFunctionality.getType());
            projectFunctionality.setShowMe(
                    templateFunctionality.getShowMe());
            projectFunctionality.setOwnedBy(ownedBy);
            projectFunctionality.setProcessed(false);
            projectFunctionality.setActive(false);
            projectFunctionality.setReferenceProject(project);

            TemplateFunctionality parentTemplateFunctionality =
                    templateFunctionality.getReferenceParentTemplateFunctionality();

            parents.put(templateFunctionality.getFunctionalityNumber(),
                    projectFunctionality);

            // The root contains a null parent, avoids null pointer
            if (parentTemplateFunctionality != null) {

                // Everything else has a parent,
                ProjectFunctionality parentProjectFunctionality =
                        parents.get(templateFunctionality
                                .getReferenceParentTemplateFunctionality().
                                        getFunctionalityNumber());

                // This projectFunctionality sets its parent to the
                // correct / found parent
                projectFunctionality.setReferenceParentFunctionality(
                        parentProjectFunctionality);

                // Set the other side of the relationship
                if (parentProjectFunctionality != null) {
                    parentProjectFunctionality.
                            addReferenceChildProjectFunctionality(
                                    projectFunctionality);
                }
            }
            projectFunctionalityRepository.save(projectFunctionality);

            // Check if there are any children that need to be copied

            if (templateFunctionality.getReferenceChildTemplateFunctionality().size() > 0
                    &&
                    // Avoid picking the parent, we only want anything at
                    // level 2
                    templateFunctionality.getReferenceParentTemplateFunctionality() != null) {

                for (TemplateFunctionality childTemplateFunctionality : templateFunctionality
                        .getReferenceChildTemplateFunctionality()) {

                    existingProjectFunctionality
                            = parents.get(templateFunctionality.getFunctionalityNumber());

                    if (existingProjectFunctionality != null) {
                        continue;
                    }

                    ProjectFunctionality childProjectFunctionality =
                            new ProjectFunctionality();

                    childProjectFunctionality.setFunctionalityNumber(
                            childTemplateFunctionality.getFunctionalityNumber());
                    childProjectFunctionality.setTitle(
                            childTemplateFunctionality.getTitle());
                    childProjectFunctionality.setConsequence(
                            childTemplateFunctionality.getConsequence());
                    childProjectFunctionality.setDescription(
                            childTemplateFunctionality.getDescription());
                    childProjectFunctionality.setExplanation(
                            childTemplateFunctionality.getExplanation());
                    childProjectFunctionality.setType(
                            childTemplateFunctionality.getType());
                    childProjectFunctionality.setOwnedBy(ownedBy);
                    childProjectFunctionality.setShowMe(
                            childTemplateFunctionality.getShowMe());
                    childProjectFunctionality.setProcessed(false);
                    childProjectFunctionality.setActive(false);
                    projectFunctionality
                            .addReferenceChildProjectFunctionality(childProjectFunctionality);
                    childProjectFunctionality.setReferenceParentFunctionality(projectFunctionality);

                    parents.put(templateFunctionality.getFunctionalityNumber(),
                            childProjectFunctionality);

                    projectFunctionalityRepository.save
                            (childProjectFunctionality);
                }
            }
        }

        ArrayList<TemplateRequirement> templateRequirements =
                (ArrayList<TemplateRequirement>) requirementRepository.findAll();
        for (TemplateRequirement templateRequirement : templateRequirements) {
            ProjectRequirement projectRequirement = new ProjectRequirement();
            projectRequirement.setReferenceProject(project);
            projectRequirement.setOrder(templateRequirement.getShowOrder());
            projectRequirement.setPriority(templateRequirement.getPriority());
            projectRequirement.setOwnedBy(ownedBy);
            projectRequirement.setRequirementText(
                    templateRequirement.getRequirementText());

            TemplateFunctionality templateFunctionality = templateRequirement.getFunctionality();
            String functionalityNumber = templateFunctionality
                    .getFunctionalityNumber();


            List<ProjectFunctionality> projectFunctionalityList =
                    projectFunctionalityRepository.
                            findByFunctionalityNumberAndReferenceProject(
                                    functionalityNumber, project);

            ProjectFunctionality projectFunctionality =
                    projectFunctionalityList.get(0);

            projectRequirement.setReferenceFunctionality(
                    projectFunctionality);

            projectFunctionality.
                    addReferenceProjectRequirement(projectRequirement);

            projectRequirementRepository.save(projectRequirement);
        }
        return project;
    }

    @Override
    public Project update(Long id, PatchObjects patchObjects)
            throws EntityNotFoundException {
        return (Project) handlePatch(getProjectOrThrow(id), patchObjects);
    }

    @Override
    public List<Project> findByOwnedBy(String ownedBy) {
        return projectRepository.findByOwnedBy(ownedBy);
    }

    /**
     * delete the project identified by the id. Find all children related to
     * the project. These are projectRequirements and projectFunctionality.
     * These are deleted first before the project is deleted
     *
     * @param id the id of the project ot delete
     */
    @Override
    public void delete(Long id) {
        Project project = getProjectOrThrow(id);
        for (ProjectFunctionality projectFunctionality :
                project.getReferenceProjectFunctionality()) {
            for (ProjectRequirement projectRequirement :
                    projectFunctionality.getReferenceProjectRequirement()) {
                projectRequirementService
                        .deleteRequirementByObject(projectRequirement);
            }
            projectFunctionalityRepository.delete(projectFunctionality);
        }
        projectRepository.delete(project);
    }

    @Override
    protected boolean checkColumnUpdatable(String path) {
        return allowableColumns.contains(path);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid Project back. If there is no valid
     * Project, a EntityNotFoundException exception is thrown
     *
     * @param id The systemId of the project object to retrieve
     * @return the project object
     */
    private Project getProjectOrThrow(@NotNull Long id)
            throws EntityNotFoundException {
        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No Project exists with Id " + id));
    }
}
