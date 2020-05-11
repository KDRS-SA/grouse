package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.links.LinksTemplateRequirement;
import no.kdrs.grouse.service.interfaces.ITemplateRequirementService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = SLASH + TEMPLATE_REQUIREMENT)
public class TemplateRequirementController {

    private ITemplateRequirementService templateRequirementService;
    private CommonController commonController;

    public TemplateRequirementController(
            ITemplateRequirementService templateRequirementService,
            CommonController commonController) {
        this.templateRequirementService = templateRequirementService;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<LinksTemplateRequirement> getRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {
        return commonController.addTemplateRequirementLinks(
                templateRequirementService.findById(requirementNumber), OK);
    }

    @PatchMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<LinksTemplateRequirement> patchRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber,
            @RequestBody PatchObjects patchObjects) {
        return commonController.addTemplateRequirementLinks(
                templateRequirementService.updateTemplateRequirement(
                        patchObjects, requirementNumber), OK);
    }

    @DeleteMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<String>
    deleteTemplateRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {
        templateRequirementService.delete(requirementNumber);
        return ResponseEntity
                .status(NO_CONTENT)
                .body("{\"status\" : \"Requirement " + requirementNumber +
                        " was deleted\"}");
    }
}
