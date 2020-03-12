package no.kdrs.grouse.service;

import no.kdrs.grouse.model.*;
import no.kdrs.grouse.persistence.*;
import no.kdrs.grouse.service.interfaces.IProjectRequirementService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.service.interfaces.ITemplateService;
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
import java.util.List;
import java.util.UUID;

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
    private ITemplateService templateService;

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
            IProjectFunctionalityRepository projectFunctionalityRepository,
            ITemplateService templateService) {
        this.entityManager = entityManager;
        this.projectRequirementService = projectRequirementService;
        this.projectRepository = projectRepository;
        this.requirementRepository = requirementRepository;
        this.functionalityRepository = functionalityRepository;
        this.projectRequirementRepository = projectRequirementRepository;
        this.projectFunctionalityRepository = projectFunctionalityRepository;
        this.templateService = templateService;
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
     * Create a new empty project that is not based on a template
     *
     * @param project
     * @return
     */
    public Project createProject(Project project) {
        String ownedBy = getUser();
        project.setFileName(project.getProjectName());
        return projectRepository.save(project);
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
     * @param templateId The id of the template to copy
     * @return The project after it was copied from a template
     */
    @Override
    public Project createProjectFromTemplate(Project project, UUID templateId) {
        Template template = templateService.findById(templateId);
        project.setDocumentCreated(false);
        if (project.getProjectName() == null) {
            project.setProjectName(template.getTemplateName());
        }
        project.setFileName(project.getProjectName());
        project.setOwnedBy(getUser());
        project = projectRepository.save(project);

        for (TemplateFunctionality templateFunctionality :
                template.getReferenceTemplateFunctionality()) {
            processFunctionalities(project, templateFunctionality
                    .getReferenceChildTemplateFunctionality());
        }
        return project;
    }

    private void processFunctionalities(
            Project project, List<TemplateFunctionality> functionalities) {

        for (TemplateFunctionality templateFunctionality : functionalities) {

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
            projectFunctionality.setOwnedBy(getUser());
            projectFunctionality.setProcessed(false);
            projectFunctionality.setActive(false);
            projectFunctionality.setReferenceProject(project);
            projectFunctionality =
                    projectFunctionalityRepository.save(projectFunctionality);
            // copy requirements if present
            processRequirements(projectFunctionality, templateFunctionality
                    .getReferenceFunctionalityRequirement());
            // Only the top functionality should have a reference to the project
            processFunctionalities(null, templateFunctionality
                    .getReferenceChildTemplateFunctionality());
        }
    }

    private void processRequirements(
            ProjectFunctionality projectFunctionality,
            List<TemplateRequirement> templateRequirements) {

        for (TemplateRequirement templateRequirement : templateRequirements) {
            ProjectRequirement projectRequirement = new ProjectRequirement();
            projectRequirement.setOrder(templateRequirement.getShowOrder());
            projectRequirement.setPriority(templateRequirement.getPriority());
            projectRequirement.setOwnedBy(getUser());
            projectRequirement.setRequirementText(
                    templateRequirement.getRequirementText());
            projectRequirement.setReferenceFunctionality(projectFunctionality);
            projectRequirementRepository.save(projectRequirement);
        }
    }

    @Override
    public Project update(Long id, PatchObjects patchObjects)
            throws EntityNotFoundException {
        return (Project) handlePatch(getProjectOrThrow(id), patchObjects);
    }

    @Override
    public Page<Project> findByOwnedBy(String ownedBy, Pageable pageable) {
        return projectRepository.findByOwnedBy(ownedBy, pageable);
    }

    @Override
    public Iterable<Project> findByOwnedBy(String username) {
        return projectRepository.findByOwnedBy(username);
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


/*


        List<TemplateFunctionality> functionalities =
                template.getReferenceTemplateFunctionality();

        System.out.println(functionalities.size());
        HashMap<String, ProjectFunctionality> parents = new HashMap<>();



        for (TemplateFunctionality templateFunctionality : functionalities) {

            ProjectFunctionality existingProjectFunctionality
                    = parents.get(
                    templateFunctionality.getFunctionalityNumber());

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


 */