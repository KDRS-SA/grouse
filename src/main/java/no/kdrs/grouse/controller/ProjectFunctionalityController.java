
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.model.links.LinksProjectFunctionality;
import no.kdrs.grouse.model.links.LinksProjectRequirement;
import no.kdrs.grouse.service.interfaces.IProjectFunctionalityService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
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

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER)
    public ResponseEntity<LinksProjectFunctionality>
    getProjectFunctionality(
            @PathVariable(FUNCTIONALITY) Long projectFunctionalityId) {
        return commonController.addProjectFunctionalityLinks(
                projectFunctionalityService.getProjectFunctionality(
                        projectFunctionalityId), OK);
    }

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + REQUIREMENT)
    public ResponseEntity<PagedModel<LinksProjectRequirement>>
    getProjectRequirements(
            Pageable page,
            @PathVariable(FUNCTIONALITY) Long projectFunctionalityId) {

        return commonController.addPagedProjectRequirementLinks(
                projectFunctionalityService.getRequirements(page,
                        projectFunctionalityId), OK);

                /*


        ProjectFunctionality projectFunctionality = projectFunctionalityService.
                getProjectFunctionality(projectFunctionalityId);

        List<ProjectRequirement> projectRequirements =
                projectFunctionality.getReferenceProjectRequirement();


        for (ProjectRequirement projectRequirement : projectRequirements) {
            projectRequirement.add(
                    linkTo(
                            methodOn(ProjectRequirementController.class)
                                    .getRequirement(projectRequirement
                                            .getRequirementId())).withSelfRel());
        }

        projectFunctionality.add(linkTo(methodOn
                (ProjectFunctionalityController.class)
                .getProjectFunctionality(projectFunctionalityId)).withSelfRel());

        projectFunctionality.add(linkTo(methodOn
                (ProjectFunctionalityController.class).
                getProjectFunctionality(projectFunctionalityId)).
                withRel(PROJECT_REQUIREMENT));
*/
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

    @PostMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + REQUIREMENT)
    public ResponseEntity<LinksProjectRequirement> createProjectRequirement(
            @PathVariable(FUNCTIONALITY) Long projectFunctionalityId,
            @RequestBody ProjectRequirement projectRequirement) {

        return commonController.addProjectRequirementLinks(
                projectFunctionalityService.
                        createProjectRequirement(projectFunctionalityId,
                                projectRequirement), CREATED);
    }

    @PatchMapping(value = FUNCTIONALITY_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<LinksProjectFunctionality> patchFunctionality(
            @PathVariable(FUNCTIONALITY) Long functionalityNumber,
            @RequestBody PatchObjects patchObjects)
            throws Exception {
        return commonController.addProjectFunctionalityLinks(
                projectFunctionalityService.updateProjectFunctionality(
                        patchObjects, functionalityNumber), OK);
    }
}
