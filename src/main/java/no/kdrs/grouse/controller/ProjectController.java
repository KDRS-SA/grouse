
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private IProjectService projectService;
    private CommonController commonController;

    public ProjectController(
            IProjectService projectService,
            CommonController commonController) {
        this.projectService = projectService;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<LinksProject> getProject(
            @PathVariable(PROJECT_NUMBER) Long projectId) {
        return commonController.addProjectLinks(projectService
                .findById(projectId), OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<LinksProject>>
    getProject(Pageable pageable) {
        return commonController.addPagedProjectLinks(projectService
                .findAll(pageable), OK);
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
        return commonController.addProjectLinks(projectService.update(
                projectId, patchObjects), OK);
    }

    @PostMapping
    public ResponseEntity<LinksProject> createProject(
            @RequestBody Project project) {
        return commonController.addProjectLinks(
                projectService.createProject(project), CREATED);
    }

    @PostMapping(value = SLASH + PROJECT_NUMBER_PARAMETER + SLASH +
            FUNCTIONALITY)
    public ResponseEntity<ProjectFunctionality> createFunctionality(
            @PathVariable(PROJECT_NUMBER) Long projectId,
            @RequestBody ProjectFunctionality projectFunctionality) {
        return ResponseEntity.status(CREATED).body(null);
    }

    @DeleteMapping(SLASH + PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<Void> deleteProject(
            @PathVariable(PROJECT_NUMBER) Long projectId) {
        projectService.delete(projectId);
        return ResponseEntity.status(NO_CONTENT)
                .body(null);
    }
}
