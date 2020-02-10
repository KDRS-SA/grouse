
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.service.interfaces.IProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Created by tsodring on 9/25/17.
 */
@RestController
@RequestMapping(value = PROJECT + SLASH)
public class ProjectController {

    private IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = PROJECT_NUMBER_PARAMETER)
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

    @GetMapping(value = PROJECT_NUMBER_PARAMETER + SLASH +
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

    @GetMapping(value = PROJECT_NUMBER_PARAMETER + FUNCTIONALITY)
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

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestBody Project project) throws Exception {

        projectService.createProject(project);

        project.add(linkTo(methodOn(ProjectController.class).
                getProject(project.getProjectId())).withSelfRel());

        project.add(linkTo(methodOn(ProjectController.class).
                getFunctionalityForProject(project.getProjectId()))
                .withRel(FUNCTIONALITY));

        project.add(linkTo(DocumentController.class, DocumentController.class.
                        getMethod("downloadDocument", Long.class),
                project.getProjectId()).
                withRel(DOCUMENT));

        return ResponseEntity.status(CREATED).body(project);
    }

    @DeleteMapping(PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<String> deleteProject(
            @PathVariable(PROJECT_NUMBER) Long projectId) {
        projectService.delete(projectId);
        return ResponseEntity.status(OK)
                .body("{\"projectId\" : " + projectId + "}" +
                        "{\"status\" : \"deleted\"}");
    }
}
