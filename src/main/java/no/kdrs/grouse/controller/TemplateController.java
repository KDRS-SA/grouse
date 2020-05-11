package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.links.*;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import no.kdrs.grouse.utils.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
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
        return commonController.addTemplateLinks(templateService
                .findById(templateId), OK);
    }

    @GetMapping(value = SLASH + REL_TEMPLATE_LIST_ALL)
    public ResponseEntity<PagedModel<LinksTemplate>>
    getTemplates(Pageable pageable) {
        return commonController.addPagedTemplateLinks(templateService
                .findAll(pageable), OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<LinksTemplate>>
    getTemplatesForUser(Pageable pageable) {
        return commonController.addPagedTemplateLinks(templateService
                .findAllForUser(pageable), OK);
    }

    @GetMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH +
            FUNCTIONALITY)
    public ResponseEntity<PagedModel<LinksTemplateFunctionality>>
    getFunctionalityForTemplate(
            Pageable pageable,
            @PathVariable(TEMPLATE_ID) UUID templateId) {
        return commonController.addPagedTemplateFunctionalityLinks(
                templateService.findFunctionalityForTemplate(
                        pageable, templateId), OK);
    }

    @GetMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH + ACCESS)
    public ResponseEntity<PagedModel<LinksUser>> getTemplateUsers(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            Pageable pageable) {
        return commonController.addPagedUserLinks(templateService
                .getTemplateUsers(templateId, pageable), OK);
    }

    @PostMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH + SHARE +
            SLASH + USER_PARAMETER)
    public ResponseEntity<LinksAccessControl> shareTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            @PathVariable(USER) String username) {
        if (USER_EMAIL_ADDRESS.equalsIgnoreCase(username)) {
            throw new BadRequestException("Cannot reuse user_email_address " +
                    "template");
        }
        return commonController.addACLLinks(templateService
                .shareTemplate(templateId, username), OK);
    }

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
    public ResponseEntity<LinksTemplateFunctionality> createFunctionality(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            @RequestBody TemplateFunctionality templateFunctionality) {
        return commonController.addTemplateFunctionalityLinks(templateService
                .createFunctionality(templateId, templateFunctionality), CREATED);
    }

    @PatchMapping(value = SLASH + TEMPLATE_ID_PARAMETER)
    public ResponseEntity<LinksTemplate> patchRequirement(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            @RequestBody PatchObjects patchObjects) throws Exception {
        return commonController.addTemplateLinks(templateService.update(
                templateId, patchObjects), OK);
    }

    @DeleteMapping(value = SLASH + TEMPLATE_ID_PARAMETER + SLASH + SHARE +
            SLASH + USER_PARAMETER)
    public ResponseEntity<String> deleteTemplateShare(
            @PathVariable(TEMPLATE_ID) UUID templateId,
            @PathVariable(USER) String username) {
        templateService.deleteTemplateShare(templateId, username);
        return ResponseEntity.status(NO_CONTENT)
                .body("{\"status\" : \"Share on " + templateId + " for user " +
                        username + " was deleted\"}");
    }

    @DeleteMapping(SLASH + TEMPLATE_ID_PARAMETER)
    public ResponseEntity<String> deleteTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId) {
        templateService.delete(templateId);
        return ResponseEntity.status(NO_CONTENT)
                .body("{\"status\" : \"Template " + templateId + " was " +
                        "deleted\"}");
    }
}
