package no.kdrs.grouse.service;

import no.kdrs.grouse.model.*;
import no.kdrs.grouse.persistence.*;
import no.kdrs.grouse.service.interfaces.IProjectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
@Service
@Transactional
public class ProjectService
        implements IProjectService {

    private EntityManager entityManager;
    private IProjectRepository projectRepository;
    private IRequirementRepository requirementRepository;
    private IFunctionalityRepository functionalityRepository;
    private IProjectRequirementRepository projectRequirementRepository;
    private IProjectFunctionalityRepository projectFunctionalityRepository;

    public ProjectService(
            EntityManager entityManager,
            IProjectRepository projectRepository,
            IRequirementRepository requirementRepository,
            IFunctionalityRepository functionalityRepository,
            IProjectRequirementRepository projectRequirementRepository,
            IProjectFunctionalityRepository projectFunctionalityRepository) {
        this.entityManager = entityManager;
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
    @SuppressWarnings("unchecked")
    public List<ProjectFunctionality> findFunctionalityForProjectByType(
            Long projectId, String type) {

        Project project = new Project();
        project.setProjectId(projectId);

        List<ProjectFunctionality> projectRequirements =
                projectFunctionalityRepository.
                        findByReferenceProjectAndTypeAndShowMe(project,
                                type, true);

        return projectRequirements;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> findAll() {
        return (ArrayList) projectRepository.findAll();
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
     * 3. Copy all Functionality objects and create ProjectFunctionality objects
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
    @SuppressWarnings("unchecked")
    public void createProject_A(Project project) {
        project.setCreatedDate(new Date());
        project.setChangedDate(new Date());
        project.setDocumentCreated(false);

        // TODO: Fixed value but can be overriden
        project.setFileName("kravspec.docx");
        GrouseUser user = new GrouseUser();
        String ownedBy = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        user.setUsername(ownedBy);
        project.setReferenceUser(user);
        projectRepository.save(project);
    }

    @Override
    @SuppressWarnings("unchecked")
    /**
     *
     * It is expecting an ordering of rows in the table to ensure that it's
     * easy to pick out the child/parent relationship
     */
    public void createProject_B(Project project) {


        ArrayList<Functionality> functionalities = (ArrayList)
                functionalityRepository.
                        findAllByOrderById();

        HashMap<String, ProjectFunctionality> parents = new HashMap<>();

        for (Functionality functionality : functionalities) {

            ProjectFunctionality existingProjectFunctionality
                    = parents.get(functionality.getFunctionalityNumber());

            if (existingProjectFunctionality != null) {
                continue;
            }

            ProjectFunctionality projectFunctionality =
                    new ProjectFunctionality();

            projectFunctionality.setFunctionalityNumber(
                    functionality.getFunctionalityNumber());
            projectFunctionality.setTitle(
                    functionality.getTitle());
            projectFunctionality.setConsequence(
                    functionality.getConsequence());
            projectFunctionality.setDescription(
                    functionality.getDescription());
            projectFunctionality.setExplanation(
                    functionality.getExplanation());
            projectFunctionality.setType(
                    functionality.getType());
            projectFunctionality.setShowMe(
                    functionality.getShowMe());
            projectFunctionality.setProcessed(false);
            projectFunctionality.setActive(false);
            projectFunctionality.setReferenceProject(project);

            Functionality parentFunctionality =
                    functionality.getReferenceParentFunctionality();

            parents.put(functionality.getFunctionalityNumber(),
                    projectFunctionality);

            // The root contains a null parent, avoids null pointer
            if (parentFunctionality != null) {

                // Everything else has a parent,
                ProjectFunctionality parentProjectFunctionality =
                        parents.get(functionality
                                .getReferenceParentFunctionality().
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

            if (functionality.getReferenceChildFunctionality().size() > 0
                    &&
                    // Avoid picking the parent, we only want anything at
                    // level 2
                    functionality.getReferenceParentFunctionality() != null) {

                for (Functionality childFunctionality : functionality
                        .getReferenceChildFunctionality()) {

                    existingProjectFunctionality
                            = parents.get(functionality.getFunctionalityNumber());

                    if (existingProjectFunctionality != null) {
                        continue;
                    }

                    ProjectFunctionality childProjectFunctionality =
                            new ProjectFunctionality();

                    childProjectFunctionality.setFunctionalityNumber(
                            childFunctionality.getFunctionalityNumber());
                    childProjectFunctionality.setTitle(
                            childFunctionality.getTitle());
                    childProjectFunctionality.setConsequence(
                            childFunctionality.getConsequence());
                    childProjectFunctionality.setDescription(
                            childFunctionality.getDescription());
                    childProjectFunctionality.setExplanation(
                            childFunctionality.getExplanation());
                    childProjectFunctionality.setType(
                            childFunctionality.getType());
                    childProjectFunctionality.setShowMe(
                            childFunctionality.getShowMe());

                    childProjectFunctionality.setProcessed(false);
                    childProjectFunctionality.setActive(false);
                    projectFunctionality
                            .addReferenceChildProjectFunctionality(childProjectFunctionality);
                    childProjectFunctionality.setReferenceParentFunctionality(projectFunctionality);

                    parents.put(functionality.getFunctionalityNumber(),
                            childProjectFunctionality);

                    projectFunctionalityRepository.save
                            (childProjectFunctionality);
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void createProject_C(Project project) {
        ArrayList<Requirement> requirements =
                (ArrayList) requirementRepository.findAll();
        for (Requirement requirement : requirements) {
            ProjectRequirement projectRequirement = new ProjectRequirement();
            projectRequirement.setReferenceProject(project);
            projectRequirement.setOrder(requirement.getShowOrder());
            projectRequirement.setPriority(requirement.getPriority());
            projectRequirement.setRequirementText(
                    requirement.getRequirementText());

            Functionality functionality = requirement.getFunctionality();
            String functionalityNumber = functionality
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
    }

    @Override
    public Project update(Long id, Project project)
            throws EntityNotFoundException {

        Project originalProject = getProjectOrThrow(id);
        // copy the values over
        originalProject.setFileName(project.getFileName());
        originalProject.setProjectName(project.getProjectName());

        // probably don't want to expose this one
        //originalProject.ListProjectOwner(project.getProjectOwner());
        return originalProject;
    }

    @Override
    public List<Project> findByReferenceUser(GrouseUser user) {
        return projectRepository.findByReferenceUser(user);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
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
