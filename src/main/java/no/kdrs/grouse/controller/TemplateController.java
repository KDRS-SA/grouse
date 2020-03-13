package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.model.links.LinksTemplate;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = SLASH + TEMPLATE)
public class TemplateController {

    private ITemplateService templateService;
    private IProjectService projectService;
    private CommonController commonController;

    public TemplateController(
            ITemplateService templateService,
            IProjectService projectService,
            CommonController commonController) {
        this.templateService = templateService;
        this.projectService = projectService;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + TEMPLATE_ID_PARAMETER)
    public ResponseEntity<LinksTemplate> getTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId) {
        return commonController.
                addTemplateLinks(templateService.findById(templateId), OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<LinksTemplate>>
    getTemplates(Pageable pageable) {
        return commonController.addPagedTemplateLinks(
                templateService.findAll(pageable), OK);
    }
/*
    @GetMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<PagedModel<TemplateFunctionality>>
    getFunctionalityForTemplate(
            Pageable pageable,
            @PathVariable(TEMPLATE_ID) UUID templateId) {

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
*/
    @PostMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH + PROJECT)
    public ResponseEntity<LinksProject> createProjectFromTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            @RequestBody Project project) {
        return commonController.addProjectLinks(
                projectService.createProjectFromTemplate(project, templateId),
                CREATED);
    }

    @PostMapping
    public ResponseEntity<LinksTemplate> createTemplate(
            @RequestBody Template template) {
        return commonController.addTemplateLinks(
                templateService.createTemplate(template), CREATED);
    }

    @PostMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<TemplateFunctionality> createFunctionality(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            @RequestBody TemplateFunctionality templateFunctionality) {

        templateService.createFunctionality(templateId, templateFunctionality);

        templateFunctionality.add(linkTo(methodOn
                (TemplateFunctionalityController.class)
                .getTemplateFunctionality(templateFunctionality
                        .getFunctionalityId()))
                .withSelfRel());
        return ResponseEntity.status(CREATED).body(templateFunctionality);
    }

    @PatchMapping(value = SLASH + TEMPLATE_ID_PARAMETER)
    public ResponseEntity<LinksTemplate> patchRequirement(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            @RequestBody PatchObjects patchObjects) throws Exception {
        return commonController.addTemplateLinks(templateService.update(
                templateId, patchObjects), OK);
    }

    @DeleteMapping(SLASH + TEMPLATE_ID_PARAMETER)
    public ResponseEntity<Void> deleteTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId) {
        templateService.delete(templateId);
        return ResponseEntity.status(NO_CONTENT)
                .body(null);
    }
}
