
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.service.interfaces.ITemplateRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;


/**
 * Created by tsodring on 29/03/18.
 */
@RestController
@RequestMapping(value = SLASH + TEMPLATE_REQUIREMENT)
public class TemplateRequirementController {

    private ITemplateRequirementService templateRequirementService;

    public TemplateRequirementController(
            ITemplateRequirementService templateRequirementService) {
        this.templateRequirementService = templateRequirementService;
    }

    @GetMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<TemplateRequirement> getRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {

        TemplateRequirement templateRequirement = templateRequirementService.
                findById(requirementNumber);

        templateRequirement.add(linkTo(methodOn
                (TemplateRequirementController.class).
                getRequirement(templateRequirement.getRequirementId())).
                withSelfRel());

        return ResponseEntity.status(OK)
                .body(templateRequirement);
    }

    @PatchMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<TemplateRequirement> patchRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber,
            @RequestBody PatchObjects patchObjects) {

        TemplateRequirement templateRequirement = templateRequirementService.
                updateTemplateRequirement(patchObjects, requirementNumber);

        templateRequirement.add(linkTo(methodOn
                (TemplateRequirementController.class).
                getRequirement(templateRequirement.getRequirementId())).
                withSelfRel());

        return ResponseEntity.status(OK)
                .body(templateRequirement);
    }

    @PostMapping(value = "/{prosjekt}/funksjonalitet/{funksjonalitet}")
    public ResponseEntity<TemplateRequirement> createRequirement(
            @PathVariable("prosjekt") Long templateId,
            @PathVariable("funksjonalitet") String functionality,
            @RequestBody TemplateRequirement templateRequirement) {

        templateRequirementService.save(templateRequirement);

        templateRequirement.add(linkTo(methodOn
                (TemplateRequirementController.class).
                getRequirement(templateRequirement.getRequirementId())).
                withSelfRel());

        return ResponseEntity.status(CREATED)
                .eTag(templateRequirement.getVersion().toString())
                .body(templateRequirement);
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
