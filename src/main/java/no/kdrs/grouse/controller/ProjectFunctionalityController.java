
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.ProjectFunctionality;
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

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<PagedModel<LinksProjectFunctionality>>
    getProjectChildFunctionality(
            Pageable pageable,
            @PathVariable(FUNCTIONALITY) Long projectFunctionalityId) {
        return commonController.addPagedProjectFunctionalityLinks(
                projectFunctionalityService.getChildFunctionality(pageable,
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

    @PostMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<LinksProjectFunctionality> createProjectFunctionality(
            @PathVariable(FUNCTIONALITY) Long projectFunctionalityId,
            @RequestBody ProjectFunctionality projectFunctionality) {
        return commonController.addProjectFunctionalityLinks(
                projectFunctionalityService.
                        createChildFunctionality(projectFunctionalityId,
                                projectFunctionality), CREATED);
    }

    @PatchMapping(value = FUNCTIONALITY_PARAMETER)
    public ResponseEntity<LinksProjectFunctionality> patchFunctionality(
            @PathVariable(FUNCTIONALITY) Long functionalityNumber,
            @RequestBody PatchObjects patchObjects)
            throws Exception {
        return commonController.addProjectFunctionalityLinks(
                projectFunctionalityService.updateProjectFunctionality(
                        patchObjects, functionalityNumber), OK);
    }
}
