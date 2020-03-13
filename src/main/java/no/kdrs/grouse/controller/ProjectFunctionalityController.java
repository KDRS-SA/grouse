
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.model.links.LinksProjectFunctionality;
import no.kdrs.grouse.service.interfaces.IProjectFunctionalityService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by tsodring on 29/03/18.
 */
@RestController
@RequestMapping(value = SLASH + PROJECT_FUNCTIONALITY)
public class ProjectFunctionalityController {

    private IProjectFunctionalityService projectFunctionalityService;
    private CommonController commonController;

    public ProjectFunctionalityController(
            IProjectFunctionalityService projectFunctionalityService,
            CommonController commonController) {
        this.projectFunctionalityService = projectFunctionalityService;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + REQUIREMENT_PARAMETER + SLASH + REQUIREMENT)
    public ResponseEntity<ProjectFunctionality> getProjectFunctionality(
            @PathVariable(REQUIREMENT) Long projectFunctionalityId) {

        ProjectFunctionality projectFunctionality = projectFunctionalityService.
                getProjectFunctionality(projectFunctionalityId);

        List<ProjectRequirement> projectRequirements =
                projectFunctionality.getReferenceProjectRequirement();

        for (ProjectRequirement projectRequirement : projectRequirements) {
            projectRequirement.add(
                    linkTo(
                            methodOn(ProjectRequirementController.class)
                                    .getRequirement(projectRequirement
                                            .getProjectRequirementId())).withSelfRel());
        }
        projectFunctionality.add(linkTo(methodOn
                (ProjectFunctionalityController.class).
                getProjectFunctionality(projectFunctionalityId)).withSelfRel());

        projectFunctionality.add(linkTo(methodOn
                (ProjectFunctionalityController.class).
                getProjectFunctionality(projectFunctionalityId)).
                withRel(PROJECT_REQUIREMENT));

        return ResponseEntity.status(OK)
                .body(projectFunctionality);
    }

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<PagedModel<LinksProjectFunctionality>>
    getChildFunctionality(
            Pageable pageable,
            @PathVariable(FUNCTIONALITY) Long projectFunctionalityId) {

        return commonController.addPagedProjectFunctionalityLinks(
                projectFunctionalityService
                        .getChildFunctionality(pageable,
                                projectFunctionalityId), OK);
/*
        List<ProjectRequirement> projectRequirements =
                projectFunctionality.getReferenceProjectRequirement();

        for (ProjectRequirement projectRequirement : projectRequirements) {
            projectRequirement.add(
                    linkTo(
                            methodOn(ProjectRequirementController.class)
                                    .getRequirement(projectRequirement
                                            .getProjectRequirementId())).withSelfRel());
        }
        projectFunctionality.add(linkTo(methodOn
                (ProjectFunctionalityController.class).
                getProjectFunctionality(projectFunctionalityId)).withSelfRel());

        projectFunctionality.add(linkTo(methodOn
                (ProjectFunctionalityController.class).
                getProjectFunctionality(projectFunctionalityId)).
                withRel(PROJECT_REQUIREMENT));

        return ResponseEntity.status(OK)
                .body(projectFunctionality);
  */

    }

    @PostMapping(value = SLASH + REQUIREMENT_PARAMETER + SLASH + REQUIREMENT)
    public ResponseEntity<ProjectRequirement> createProjectRequirement(
            @PathVariable(REQUIREMENT) Long projectFunctionalityId,
            @RequestBody ProjectRequirement projectRequirement) {

        projectFunctionalityService.
                createProjectRequirement(projectFunctionalityId,
                        projectRequirement);

        projectRequirement.add(linkTo(methodOn
                (ProjectRequirementController.class).
                getRequirement(projectRequirement.
                        getProjectRequirementId())).withSelfRel());

        return ResponseEntity.status(CREATED)
                .body(projectRequirement);
    }

    @PatchMapping(value = REQUIREMENT_PARAMETER + SLASH + REQUIREMENT)
    public ResponseEntity<ProjectFunctionality> patchFunctionality(
            @PathVariable(REQUIREMENT) Long functionalityNumber,
            @RequestBody PatchObjects patchObjects)
            throws Exception {

        ProjectFunctionality projectFunctionality = projectFunctionalityService.
                updateProjectFunctionality(patchObjects, functionalityNumber);

        projectFunctionality.add(linkTo(methodOn
                (ProjectFunctionalityController.class).
                getProjectFunctionality(projectFunctionality
                        .getProjectFunctionalityId())).
                withSelfRel());

        return ResponseEntity.status(OK)
                .body(projectFunctionality);
    }
}
