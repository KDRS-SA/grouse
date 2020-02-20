
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = SLASH + TEMPLATE + SLASH)
public class TemplateController {

    private ITemplateService templateService;

    public TemplateController(ITemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping(value = TEMPLATE_ID_PARAMETER)
    public ResponseEntity<Template> getTemplate(
            @PathVariable(TEMPLATE_ID) Long templateId) {
        Template template = templateService.findById(templateId);
        template.add(linkTo(methodOn(TemplateController.class).
                getTemplate(templateId)).withSelfRel());
        template.add(linkTo(methodOn(TemplateController.class).
                getFunctionalityForTemplate(templateId))
                .withRel(FUNCTIONALITY));
        return ResponseEntity.status(OK)
                .body(template);
    }

    @GetMapping(value = TEMPLATE_ID_PARAMETER + SLASH +
            FUNCTIONALITY + FUNCTIONALITY_PARAMETER)
    public ResponseEntity<List<TemplateRequirement>>
    getRequirementsForFunctionality(
            @PathVariable(TEMPLATE_ID) Long templateId,
            @PathVariable(FUNCTIONALITY) String functionalityNumber) {

        List<TemplateRequirement> templateRequirements =
                templateService.findByTemplateIdOrderByTemplateName(
                        templateId, functionalityNumber);

        return ResponseEntity.status(OK)
                .body(templateRequirements);
    }

    @GetMapping(value = TEMPLATE_ID_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<List<TemplateFunctionality>>
    getFunctionalityForTemplate(
            @PathVariable(TEMPLATE_ID) Long templateId) {

        List<TemplateFunctionality> templateFunctionalities =
                templateService.findFunctionalityForTemplateByType(templateId,
                        "mainmenu");

        for (TemplateFunctionality templateFunctionality :
                templateFunctionalities) {

            for (TemplateFunctionality childTemplateFunctionality :
                    templateFunctionality
                            .getReferenceChildTemplateFunctionality()) {

                // Add REL to retrieve all requirements
                childTemplateFunctionality.add(linkTo(methodOn(
                        TemplateFunctionalityController.class).
                        getTemplateFunctionality(templateFunctionality.
                                getFunctionalityId())).
                        withRel(REQUIREMENT));

                childTemplateFunctionality.add(linkTo(methodOn
                        (TemplateFunctionalityController.class).
                        getTemplateFunctionality(childTemplateFunctionality.
                                getFunctionalityId())).withSelfRel());


                // Add SELF REL to each templateRequirement at this level
                for (TemplateRequirement templateRequirement :
                        childTemplateFunctionality
                                .getReferenceFunctionalityRequirement()) {
                    templateRequirement.add(linkTo(methodOn(
                            TemplateRequirementController.class).
                            getRequirement(templateRequirement.
                                    getRequirementId())).withSelfRel());
                }

                for (TemplateFunctionality childChildTemplateFunctionality2 :
                        childTemplateFunctionality
                                .getReferenceChildTemplateFunctionality()) {

                    childChildTemplateFunctionality2.add(linkTo(methodOn
                            (TemplateFunctionalityController.class).
                            getTemplateFunctionality(childChildTemplateFunctionality2.
                                    getFunctionalityId())).withSelfRel());

                    for (TemplateRequirement templateRequirement :
                            childChildTemplateFunctionality2
                                    .getReferenceFunctionalityRequirement()) {
                        templateRequirement.add(linkTo(methodOn(
                                TemplateRequirementController.class).
                                getRequirement(templateRequirement.
                                        getRequirementId())).withSelfRel());
                    }
                }
            }

            // Add REL to retrieve all requirements
            templateFunctionality.add(linkTo(methodOn(
                    TemplateFunctionalityController.class).
                    getTemplateFunctionality(templateFunctionality.
                            getFunctionalityId())).
                    withRel(REQUIREMENT));
            templateFunctionality.add(linkTo(methodOn
                    (TemplateFunctionalityController.class).
                    getTemplateFunctionality(templateFunctionality.
                            getFunctionalityId())).withSelfRel());
        }
        return ResponseEntity.status(OK)
                .body(templateFunctionalities);
    }

    @PostMapping
    public ResponseEntity<Template> createTemplate(
            @RequestBody Template template) throws Exception {

        templateService.createTemplate(template);

        template.add(linkTo(methodOn(TemplateController.class)
                .getTemplate(template.getTemplateId())).withSelfRel());

        template.add(linkTo(methodOn(TemplateController.class)
                .getFunctionalityForTemplate(template.getTemplateId()))
                .withRel(FUNCTIONALITY));

        template.add(linkTo(methodOn(DocumentController.class)
                .downloadDocument(template.getTemplateId()))
                .withRel(DOCUMENT));

        return ResponseEntity.status(CREATED).body(template);
    }

    @PostMapping(value = TEMPLATE_ID_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<TemplateFunctionality> createFunctionality(
            @PathVariable(TEMPLATE_ID) Long templateId,
            @RequestBody TemplateFunctionality templateFunctionality) {

        templateService.createFunctionality(templateId, templateFunctionality);

        templateFunctionality.add(linkTo(methodOn
                (TemplateFunctionalityController.class)
                .getTemplateFunctionality(templateFunctionality
                        .getFunctionalityId()))
                .withSelfRel());
        return ResponseEntity.status(CREATED).body(templateFunctionality);
    }

    @DeleteMapping(TEMPLATE_ID_PARAMETER)
    public ResponseEntity<String> deleteTemplate(
            @PathVariable(TEMPLATE_ID) Long templateId) {
        templateService.delete(templateId);
        return ResponseEntity.status(OK)
                .body("{\"templateId\" : " + templateId + "}" +
                        "{\"status\" : \"deleted\"}");
    }
}
