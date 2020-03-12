package no.kdrs.grouse.controller;

import no.kdrs.grouse.assemblers.TemplateAssembler;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.model.links.LinksTemplate;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import no.kdrs.grouse.utils.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = SLASH + TEMPLATE)
public class TemplateController {

    private static final Logger logger =
            LoggerFactory.getLogger(TemplateController.class);

    private ITemplateService templateService;
    private IProjectService projectService;
    private PagedResourcesAssembler<Template> pagedResourcesAssembler;
    private TemplateAssembler templateAssembler;
    private CommonController commonController;

    public TemplateController(
            ITemplateService templateService,
            IProjectService projectService,
            PagedResourcesAssembler<Template> pagedResourcesAssembler,
            TemplateAssembler templateAssembler,
            CommonController commonController) {
        this.templateService = templateService;
        this.projectService = projectService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.templateAssembler = templateAssembler;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + TEMPLATE_ID_PARAMETER)
    public ResponseEntity<LinksTemplate> getTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId) {
        return addTemplateLinks(templateService.findById(templateId), OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<LinksTemplate>>
    getTemplates(Pageable pageable) {
        Page<Template> templates = templateService.findAll(pageable);
        PagedModel<LinksTemplate> templateModels =
                pagedResourcesAssembler.toModel(templates, templateAssembler);
        return ResponseEntity.status(OK)
                .body(templateModels);
    }

    @GetMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH +
            FUNCTIONALITY + FUNCTIONALITY_PARAMETER)
    public ResponseEntity<List<TemplateRequirement>>
    getRequirementsForFunctionality(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            @PathVariable(FUNCTIONALITY) String functionalityNumber) {

        List<TemplateRequirement> templateRequirements =
                templateService.findByTemplateIdOrderByTemplateName(
                        templateId, functionalityNumber);

        return ResponseEntity.status(OK)
                .body(templateRequirements);
    }

    @GetMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH + FUNCTIONALITY)
    public ResponseEntity<List<TemplateFunctionality>>
    getFunctionalityForTemplate(
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

    @PostMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH + PROJECT)
    public ResponseEntity<LinksProject> createProjectFromTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId) {
        return commonController.addProjectLinks(
                projectService.createProjectFromTemplate(templateId), CREATED);
    }

    @PostMapping
    public ResponseEntity<LinksTemplate> createTemplate(
            @RequestBody Template template) {
        return addTemplateLinks(
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
        return addTemplateLinks(templateService.update(
                templateId, patchObjects), OK);
    }

    @DeleteMapping(SLASH + TEMPLATE_ID_PARAMETER)
    public ResponseEntity<Void> deleteTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId) {
        templateService.delete(templateId);
        return ResponseEntity.status(NO_CONTENT)
                .body(null);
    }

    private ResponseEntity<LinksTemplate> addTemplateLinks(
            @NotNull final Template template,
            @NotNull final HttpStatus status) {

        try {
            LinksTemplate linksTemplate = new LinksTemplate(template);
            linksTemplate.add(linkTo(methodOn(TemplateController.class)
                    .getTemplate(template.getTemplateId())).withSelfRel());
            linksTemplate.add(linkTo(methodOn(TemplateController.class)
                    .getFunctionalityForTemplate(template.getTemplateId()))
                    .withRel(FUNCTIONALITY));
            linksTemplate.add(linkTo(methodOn(DocumentController.class)
                    .downloadDocumentTemplate(template.getTemplateId()))
                    .withRel(DOCUMENT));
            return ResponseEntity.status(status)
                    .eTag(template.getVersion().toString())
                    .body(linksTemplate);
        } catch (IOException e) {
            String errorMessage =
                    "Error when adding template links: " + e.getMessage();
            logger.error(errorMessage);
            throw new InternalException(errorMessage);
        }
    }
}
