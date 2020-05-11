package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.service.interfaces.ITemplateRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = SLASH + REQUIREMENT)
public class RequirementController {

    private final ITemplateRequirementService requirementService;

    RequirementController(ITemplateRequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @GetMapping(value = "/{" + REQUIREMENT + ":.+}")
    public ResponseEntity<TemplateRequirement> getRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {
        return ResponseEntity.status(OK)
                .body(requirementService.
                        findById(requirementNumber));
    }

    @PatchMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<TemplateRequirement> patchRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber,
            @RequestBody PatchObjects patchObjects) {
        TemplateRequirement templateRequirement = requirementService
                .updateTemplateRequirement(patchObjects, requirementNumber);
        return ResponseEntity.status(OK)
                .eTag(templateRequirement.getVersion().toString())
                .body(templateRequirement);
    }

    @DeleteMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<String> deleteRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {
        requirementService.delete(requirementNumber);
        return ResponseEntity.status(NO_CONTENT)
                .body("{\"status\" : \"TemplateRequirement " +
                        requirementNumber + " was deleted\"}");
    }
}
