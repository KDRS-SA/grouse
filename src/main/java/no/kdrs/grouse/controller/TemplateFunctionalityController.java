
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.service.interfaces.ITemplateFunctionalityService;
import no.kdrs.grouse.utils.PatchObjects;
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
@RequestMapping(value = SLASH + TEMPLATE + SLASH + FUNCTIONALITY)
public class TemplateFunctionalityController {

    private ITemplateFunctionalityService templateFunctionalityService;

    public TemplateFunctionalityController(
            ITemplateFunctionalityService templateFunctionalityService) {
        this.templateFunctionalityService = templateFunctionalityService;
    }

    @GetMapping(value = FUNCTIONALITY_PARAMETER)
    public ResponseEntity<TemplateFunctionality> getTemplateFunctionality(
            @PathVariable(FUNCTIONALITY) Long templateFunctionalityId) {

        TemplateFunctionality templateFunctionality =
                templateFunctionalityService.findById(templateFunctionalityId);

        List<TemplateRequirement> templateRequirements =
                templateFunctionality.getReferenceFunctionalityRequirement();

        for (TemplateRequirement templateRequirement : templateRequirements) {
            templateRequirement.add(
                    linkTo(
                            methodOn(TemplateRequirementController.class)
                                    .getRequirement(templateRequirement
                                            .getRequirementId())).withSelfRel());
        }
        templateFunctionality.add(linkTo(methodOn
                (TemplateFunctionalityController.class).
                getTemplateFunctionality(templateFunctionalityId)).withSelfRel());

        templateFunctionality.add(linkTo(methodOn
                (TemplateFunctionalityController.class).
                getTemplateFunctionality(templateFunctionalityId)).
                withRel(TEMPLATE_REQUIREMENT));

        return ResponseEntity.status(OK)
                .body(templateFunctionality);
    }

    @PostMapping(value = "/{krav}/" + REQUIREMENT)
    public ResponseEntity<TemplateRequirement> createTemplateRequirement(
            @PathVariable("krav") Long templateFunctionalityId,
            @RequestBody TemplateRequirement templateRequirement) {

        templateFunctionalityService.
                createTemplateRequirement(templateFunctionalityId,
                        templateRequirement);

        templateRequirement.add(linkTo(methodOn
                (TemplateRequirementController.class).
                getRequirement(templateRequirement.
                        getRequirementId())).withSelfRel());

        return ResponseEntity.status(CREATED)
                .eTag(templateRequirement.getVersion().toString())
                .body(templateRequirement);
    }


    @PatchMapping(value = "/{krav}/krav")
    public ResponseEntity<TemplateFunctionality> patchFunctionality(
            @PathVariable("krav") Long functionalityNumber,
            @RequestBody PatchObjects patchObjects)
            throws Exception {

        TemplateFunctionality templateFunctionality =
                templateFunctionalityService.updateProjectFunctionality(
                        patchObjects, functionalityNumber);

        templateFunctionality.add(linkTo(methodOn
                (TemplateFunctionalityController.class).
                getTemplateFunctionality(templateFunctionality
                        .getFunctionalityId())).
                withSelfRel());

        return ResponseEntity.status(OK)
                .eTag(templateFunctionality.getVersion().toString())
                .body(templateFunctionality);
    }
}
