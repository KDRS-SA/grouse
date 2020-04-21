package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.model.links.LinksTemplateFunctionality;
import no.kdrs.grouse.model.links.LinksTemplateRequirement;
import no.kdrs.grouse.service.interfaces.ITemplateFunctionalityService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = SLASH + TEMPLATE + SLASH + FUNCTIONALITY)
public class TemplateFunctionalityController {

    private ITemplateFunctionalityService templateFunctionalityService;
    private CommonController commonController;

    public TemplateFunctionalityController(
            ITemplateFunctionalityService templateFunctionalityService,
            CommonController commonController) {
        this.templateFunctionalityService = templateFunctionalityService;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER)
    public ResponseEntity<LinksTemplateFunctionality>
    getTemplateFunctionality(
            @PathVariable(FUNCTIONALITY) Long templateFunctionalityId) {
        return commonController.addTemplateFunctionalityLinks(
                templateFunctionalityService.getTemplateFunctionality(
                        templateFunctionalityId), OK);
    }

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<PagedModel<LinksTemplateFunctionality>>
    getTemplateChildFunctionality(
            Pageable pageable,
            @PathVariable(FUNCTIONALITY) Long templateFunctionalityId) {
        return commonController.addPagedTemplateFunctionalityLinks(
                templateFunctionalityService.getChildFunctionality(pageable,
                        templateFunctionalityId), OK);
    }

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + REQUIREMENT)
    public ResponseEntity<PagedModel<LinksTemplateRequirement>>
    getTemplateRequirements(
            Pageable page,
            @PathVariable(FUNCTIONALITY) Long templateFunctionalityId) {
        return commonController.addPagedTemplateRequirementLinks(
                templateFunctionalityService.getRequirements(page,
                        templateFunctionalityId), OK);
    }

    @PostMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + REQUIREMENT)
    public ResponseEntity<LinksTemplateRequirement> createTemplateRequirement(
            @PathVariable(FUNCTIONALITY) Long templateFunctionalityId,
            @RequestBody TemplateRequirement templateRequirement) {
        return commonController.addTemplateRequirementLinks(
                templateFunctionalityService.
                        createTemplateRequirement(templateFunctionalityId,
                                templateRequirement), CREATED);
    }

    @PostMapping(value = SLASH + FUNCTIONALITY_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<LinksTemplateFunctionality> createTemplateFunctionality(
            @PathVariable(FUNCTIONALITY) Long templateFunctionalityId,
            @RequestBody TemplateFunctionality templateFunctionality) {
        return commonController.addTemplateFunctionalityLinks(
                templateFunctionalityService.
                        createChildFunctionality(templateFunctionalityId,
                                templateFunctionality), CREATED);
    }

    @PatchMapping(value = FUNCTIONALITY_PARAMETER)
    public ResponseEntity<LinksTemplateFunctionality> patchFunctionality(
            @PathVariable(FUNCTIONALITY) Long functionalityNumber,
            @RequestBody PatchObjects patchObjects)
            throws Exception {
        return commonController.addTemplateFunctionalityLinks(
                templateFunctionalityService.updateTemplateFunctionality(
                        patchObjects, functionalityNumber), OK);
    }
}
