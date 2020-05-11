package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.links.LinksProjectRequirement;
import no.kdrs.grouse.service.interfaces.IProjectRequirementService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = SLASH + PROJECT_REQUIREMENT)
public class ProjectRequirementController {

    private IProjectRequirementService projectRequirementService;
    private CommonController commonController;

    public ProjectRequirementController(
            IProjectRequirementService projectRequirementService,
            CommonController commonController) {
        this.projectRequirementService = projectRequirementService;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<LinksProjectRequirement> getRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {
        return commonController.addProjectRequirementLinks(
                projectRequirementService.getProjectRequirement(
                        requirementNumber), OK);
    }

    @PatchMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<LinksProjectRequirement> patchRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber,
            @RequestBody PatchObjects patchObjects)
            throws Exception {
        return commonController.addProjectRequirementLinks(
                projectRequirementService.updateProjectRequirement(
                        patchObjects, requirementNumber), OK);
    }

    @DeleteMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<String>
    deleteProjectRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {
        projectRequirementService.
                deleteProjectRequirement(requirementNumber);
        return ResponseEntity
                .status(NO_CONTENT)
                .body("{\"status\" : \"Requirement " + requirementNumber
                        + " was deleted\"}");
    }
}
