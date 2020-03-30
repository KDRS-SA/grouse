package no.kdrs.grouse.service;

import no.kdrs.grouse.model.*;
import no.kdrs.grouse.persistence.IProjectFunctionalityRepository;
import no.kdrs.grouse.persistence.IProjectRepository;
import no.kdrs.grouse.persistence.IProjectRequirementRepository;
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

import static java.util.UUID.randomUUID;

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
    private IProjectRequirementRepository projectRequirementRepository;
    private IProjectFunctionalityRepository projectFunctionalityRepository;
    private ITemplateService templateService;

    // Columns that it is possible to update via a PATCH request
    private ArrayList<String> allowableColumns =
            new ArrayList<>(Arrays.asList("projectName",
                    "fileNameInternal", "ownedBy"));

    public ProjectService(
            EntityManager entityManager,
            IProjectRequirementService projectRequirementService,
            IProjectRepository projectRepository,
            IProjectRequirementRepository projectRequirementRepository,
            IProjectFunctionalityRepository projectFunctionalityRepository,
            ITemplateService templateService) {
        this.entityManager = entityManager;
        this.projectRequirementService = projectRequirementService;
        this.projectRepository = projectRepository;
        this.projectRequirementRepository = projectRequirementRepository;
        this.projectFunctionalityRepository = projectFunctionalityRepository;
        this.templateService = templateService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProjectRequirement> findByProjectIdOrderByProjectName(
            UUID projectId, String functionalityNumber) {
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
    public Page<ProjectFunctionality> findFunctionalityForProjectByType(
            Pageable pageable, UUID projectId, String type) {
        return projectFunctionalityRepository.
                findByReferenceProjectAndTypeAndShowMe(
                        getProjectOrThrow(projectId), type, true, pageable);
    }

    @Override
    public Page<Project> findAll(Pageable page) {
        return projectRepository.findAll(page);
    }

    @Override
    public Project findById(@NotNull UUID projectId) {
        return getProjectOrThrow(projectId);
    }

    @Override
    public ProjectFunctionality createFunctionality(
            UUID projectId, ProjectFunctionality projectFunctionality) {
        Project project = getProjectOrThrow(projectId);
        project.addProjectFunctionality(projectFunctionality);
        projectFunctionality.setReferenceProject(project);
        return projectFunctionalityRepository.save(projectFunctionality);
    }

    /**
     * Create a new empty project that is not based on a template
     *
     * @param project The incoming project object
     * @return project after it is persisted
     */
    public Project createProject(Project project) {
        project.setProjectId(randomUUID());
        project.setOwnedBy(getUser());
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
        if (project.getProjectName() == null) {
            project.setProjectName(template.getTemplateName());
        }
        project.setProjectId(randomUUID());
        project.setFileName(project.getProjectName());
        project.setOwnedBy(getUser());
        project = projectRepository.save(project);

        for (TemplateFunctionality templateFunctionality :
                template.getReferenceTemplateFunctionality()) {
            processFunctionalities(project, null,
                    templateFunctionality
                            .getReferenceChildTemplateFunctionality());
        }
        return project;
    }

    private void processFunctionalities(
            Project project,
            ProjectFunctionality parentProjectFunctionality,
            List<TemplateFunctionality> functionalities) {

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

            if (parentProjectFunctionality != null) {
                projectFunctionality.setReferenceParentFunctionality(
                        parentProjectFunctionality);
            }
            projectFunctionality =
                    projectFunctionalityRepository.save(projectFunctionality);
            // copy requirements if present
            processRequirements(projectFunctionality, templateFunctionality
                    .getReferenceTemplateRequirement());
            // Only the top functionality should have a reference to the project
            processFunctionalities(null, projectFunctionality,
                    templateFunctionality
                            .getReferenceChildTemplateFunctionality());
        }
    }

    private void processRequirements(
            ProjectFunctionality projectFunctionality,
            List<TemplateRequirement> templateRequirements) {

        for (TemplateRequirement templateRequirement : templateRequirements) {
            ProjectRequirement projectRequirement = new ProjectRequirement();
            projectRequirement.setShowOrder(templateRequirement.getShowOrder());
            projectRequirement.setPriority(templateRequirement.getPriority());
            projectRequirement.setOwnedBy(getUser());
            projectRequirement.setRequirementText(
                    templateRequirement.getRequirementText());
            projectRequirement.setReferenceFunctionality(projectFunctionality);
            projectRequirementRepository.save(projectRequirement);
        }
    }

    @Override
    public Project update(UUID projectId, PatchObjects patchObjects)
            throws EntityNotFoundException {
        return (Project) handlePatch(getProjectOrThrow(projectId),
                patchObjects);
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
     * @param projectId the id of the project ot delete
     */
    @Override
    public void delete(UUID projectId) {
        Project project = getProjectOrThrow(projectId);
        deleteFunctionalities(project.getReferenceProjectFunctionality());
        projectRepository.delete(project);
    }

    protected void deleteFunctionalities(
            List<ProjectFunctionality> projectFunctionalities) {
        for (ProjectFunctionality projectFunctionality :
                projectFunctionalities) {
            if (projectFunctionality
                    .getReferenceProjectRequirement().size() > 0) {
                deleteRequirements(projectFunctionality
                        .getReferenceProjectRequirement());
            }
            if (projectFunctionality
                    .getReferenceChildProjectFunctionality().size() > 0) {
                deleteFunctionalities(projectFunctionality
                        .getReferenceChildProjectFunctionality());
            }
            projectFunctionality.setReferenceParentFunctionality(null);
            projectFunctionality.setReferenceProject(null);
            projectFunctionalityRepository.delete(projectFunctionality);
        }
    }

    protected void deleteRequirements(
            List<ProjectRequirement> projectRequirements) {
        for (ProjectRequirement projectRequirement : projectRequirements) {
            projectRequirementService
                    .deleteRequirementByObject(projectRequirement);
        }
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
     * @param projectId The id of the project object to retrieve
     * @return the project object
     */
    private Project getProjectOrThrow(@NotNull UUID projectId)
            throws EntityNotFoundException {
        return projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No Project exists with Id " + projectId));
    }
}
