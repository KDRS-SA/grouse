
package no.kdrs.grouse.controller;

import no.kdrs.grouse.assemblers.ProjectAssembler;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.utils.PatchObjects;
import no.kdrs.grouse.utils.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;

/**
 * Created by tsodring on 9/25/17.
 */
@RestController
@RequestMapping(value = SLASH + PROJECT)
public class ProjectController {

    private static final Logger logger =
            LoggerFactory.getLogger(ProjectController.class);

    private PagedResourcesAssembler<Project> pagedResourcesAssembler;
    private IProjectService projectService;
    private ProjectAssembler projectAssembler;

    public ProjectController(
            PagedResourcesAssembler<Project> pagedResourcesAssembler,
            IProjectService projectService,
            ProjectAssembler projectAssembler) {
        this.projectService = projectService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.projectAssembler = projectAssembler;
    }

    @GetMapping(value = SLASH + PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<Project> getProject(
            @PathVariable(PROJECT_NUMBER) Long projectId) {

        Project project = projectService.findById(projectId);
        project.add(linkTo(methodOn(ProjectController.class).
                getProject(projectId)).withSelfRel());
        project.add(linkTo(methodOn(ProjectController.class).
                getFunctionalityForProject(projectId))
                .withRel(FUNCTIONALITY));
        return ResponseEntity.status(OK)
                .body(project);
    }

    @GetMapping
    public ResponseEntity<PagedModel<LinksProject>>
    getProject(Pageable pageable) {
        Page<Project> projects = projectService.findAll(pageable);
        PagedModel<LinksProject> projectModels =
                pagedResourcesAssembler.toModel(projects, projectAssembler);
        return ResponseEntity.status(OK)
                .body(projectModels);
    }

    @GetMapping(value = SLASH + PROJECT_NUMBER_PARAMETER + SLASH +
            FUNCTIONALITY + FUNCTIONALITY_PARAMETER)
    public ResponseEntity<List<ProjectRequirement>>
    getRequirementsForFunctionality(
            @PathVariable(PROJECT_NUMBER) Long projectId,
            @PathVariable(FUNCTIONALITY) String functionalityNumber) {

        List<ProjectRequirement> projectRequirements =
                projectService.findByProjectIdOrderByProjectName(
                        projectId, functionalityNumber);

        return ResponseEntity.status(OK)
                .body(projectRequirements);
    }

    @GetMapping(value = SLASH + PROJECT_NUMBER_PARAMETER + SLASH +
            FUNCTIONALITY)
    public ResponseEntity<List<ProjectFunctionality>>
    getFunctionalityForProject(
            @PathVariable(PROJECT_NUMBER) Long projectId) {

        List<ProjectFunctionality> projectFunctionalities =
                projectService.findFunctionalityForProjectByType(projectId,
                        "mainmenu");

        for (ProjectFunctionality projectFunctionality :
                projectFunctionalities) {

            for (ProjectFunctionality childProjectFunctionality :
                    projectFunctionality
                            .getReferenceChildProjectFunctionality()) {

                // Add REL to retrieve all requirements
                childProjectFunctionality.add(linkTo(methodOn(
                        ProjectFunctionalityController.class).
                        getProjectFunctionality(projectFunctionality.
                                getProjectFunctionalityId())).
                        withRel(REQUIREMENT));

                childProjectFunctionality.add(linkTo(methodOn
                        (ProjectFunctionalityController.class).
                        getProjectFunctionality(childProjectFunctionality.
                                getProjectFunctionalityId())).withSelfRel());


                // Add SELF REL to each projectRequirement at this level
                for (ProjectRequirement projectRequirement :
                        childProjectFunctionality
                                .getReferenceProjectRequirement()) {
                    projectRequirement.add(linkTo(methodOn(
                            ProjectRequirementController.class).
                            getRequirement(projectRequirement.
                                    getProjectRequirementId())).withSelfRel());
                }

                for (ProjectFunctionality childChildProjectFunctionality2 :
                        childProjectFunctionality
                                .getReferenceChildProjectFunctionality()) {

                    childChildProjectFunctionality2.add(linkTo(methodOn
                            (ProjectFunctionalityController.class).
                            getProjectFunctionality(childChildProjectFunctionality2.
                                    getProjectFunctionalityId())).withSelfRel());

                    for (ProjectRequirement projectRequirement :
                            childChildProjectFunctionality2.
                                    getReferenceProjectRequirement()) {
                        projectRequirement.add(linkTo(methodOn(
                                ProjectRequirementController.class).
                                getRequirement(projectRequirement.
                                        getProjectRequirementId())).withSelfRel());
                    }
                }
            }

            // Add REL to retrieve all requirements
            projectFunctionality.add(linkTo(methodOn(
                    ProjectFunctionalityController.class).
                    getProjectFunctionality(projectFunctionality.
                            getProjectFunctionalityId())).
                    withRel(REQUIREMENT));
            projectFunctionality.add(linkTo(methodOn
                    (ProjectFunctionalityController.class).
                    getProjectFunctionality(projectFunctionality.
                            getProjectFunctionalityId())).withSelfRel());
        }
        return ResponseEntity.status(OK)
                .body(projectFunctionalities);
    }

    @PatchMapping(value = SLASH + PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<LinksProject> patchRequirement(
            @PathVariable(PROJECT_NUMBER) Long projectId,
            @RequestBody PatchObjects patchObjects) throws Exception {
        return addProjectLinks(projectService.update(
                projectId, patchObjects), OK);
    }

    @PostMapping
    public ResponseEntity<LinksProject> createProject(
            @RequestBody Project project) throws Exception {
        return addProjectLinks(
                projectService.createProject(project), CREATED);
    }

    @DeleteMapping(SLASH + PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<Void> deleteProject(
            @PathVariable(PROJECT_NUMBER) Long projectId) {
        projectService.delete(projectId);
        return ResponseEntity.status(NO_CONTENT)
                .body(null);
    }

    private ResponseEntity<LinksProject> addProjectLinks(
            @NotNull final Project project,
            @NotNull final HttpStatus status) {

        try {
            LinksProject linksProject = new LinksProject(project);
            linksProject.add(linkTo(methodOn(ProjectController.class)
                    .getProject(project.getProjectId())).withSelfRel());
            linksProject.add(linkTo(methodOn(ProjectController.class)
                    .getFunctionalityForProject(project.getProjectId()))
                    .withRel(FUNCTIONALITY));
            linksProject.add(linkTo(methodOn(DocumentController.class)
                    .downloadDocumentProject(project.getProjectId()))
                    .withRel(DOCUMENT));
            return ResponseEntity.status(status)
                    .eTag(project.getVersion().toString())
                    .body(linksProject);
        } catch (IOException e) {
            String errorMessage =
                    "Error when adding project links: " + e.getMessage();
            logger.error(errorMessage);
            throw new InternalException(errorMessage);
        }
    }
}
