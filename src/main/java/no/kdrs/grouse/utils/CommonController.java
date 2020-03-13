package no.kdrs.grouse.utils;

import no.kdrs.grouse.assemblers.ProjectAssembler;
import no.kdrs.grouse.assemblers.TemplateAssembler;
import no.kdrs.grouse.assemblers.UserAssembler;
import no.kdrs.grouse.controller.DocumentController;
import no.kdrs.grouse.controller.ProjectController;
import no.kdrs.grouse.controller.TemplateController;
import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.model.links.LinksTemplate;
import no.kdrs.grouse.model.links.LinksUser;
import no.kdrs.grouse.utils.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Utility class that deals with a lot the links stuff added to the response
 */

@Component
public class CommonController {

    public static final Logger logger =
            LoggerFactory.getLogger(CommonController.class);

    private RoleValidator role;
    private PagedResourcesAssembler<GrouseUser> pagedUserResourcesAssembler;
    private PagedResourcesAssembler<Project> pagedProjectResourcesAssembler;
    private PagedResourcesAssembler<Template> pagedTemplateResourcesAssembler;
    private ProjectAssembler projectAssembler;
    private TemplateAssembler templateAssembler;
    private UserAssembler userAssembler;

    public CommonController(
            RoleValidator role,
            PagedResourcesAssembler<GrouseUser> pagedUserResourcesAssembler,
            PagedResourcesAssembler<Project> pagedProjectResourcesAssembler,
            PagedResourcesAssembler<Template> pagedTemplateResourcesAssembler,
            ProjectAssembler projectAssembler,
            TemplateAssembler templateAssembler,
            UserAssembler userAssembler) {
        this.role = role;
        this.pagedUserResourcesAssembler = pagedUserResourcesAssembler;
        this.pagedProjectResourcesAssembler = pagedProjectResourcesAssembler;
        this.pagedTemplateResourcesAssembler = pagedTemplateResourcesAssembler;
        this.projectAssembler = projectAssembler;
        this.templateAssembler = templateAssembler;
        this.userAssembler = userAssembler;
    }

    public void checkAccess(String ownedBy) {
        String loggedInUser = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        if (!role.isAdmin()) {
            if (!loggedInUser.equals(ownedBy)) {
                logger.error(NO_ACCESS_OTHER_USER);
                throw new AccessDeniedException(NO_ACCESS_OTHER_USER);
            }
        }
    }

    public ResponseEntity<PagedModel<LinksProject>> addPagedProjectLinks(
            @NotNull final Page<Project> projects,
            @NotNull final HttpStatus status) {
        PagedModel<LinksProject> projectModels =
                pagedProjectResourcesAssembler
                        .toModel(projects, projectAssembler);
        return ResponseEntity.status(status)
                .body(projectModels);
    }

    public ResponseEntity<LinksProject> addProjectLinks(
            @NotNull final Project project,
            @NotNull final HttpStatus status) {
        LinksProject linksProject = new LinksProject(project);
        linksProject.add(linkTo(methodOn(ProjectController.class)
                .getProject(project.getProjectId())).withSelfRel());
        linksProject.add(linkTo(methodOn(ProjectController.class)
                .getFunctionalityForProject(project.getProjectId()))
                .withRel(FUNCTIONALITY));
        linksProject.add(linkTo(methodOn(DocumentController.class)
                .downloadProjectDocument(project.getProjectId()))
                .withRel(DOCUMENT));
        return ResponseEntity.status(status)
                .eTag(project.getVersion().toString())
                .body(linksProject);
    }

    public ResponseEntity<LinksUser> addUserLinks(
            @NotNull final GrouseUser user,
            @NotNull final HttpStatus status) {
        return ResponseEntity.status(status)
                .eTag(user.getVersion().toString())
                .body(userAssembler.toModel(user));
    }

    public ResponseEntity<PagedModel<LinksUser>> addPagedUserLinks(
            @NotNull final Page<GrouseUser> users,
            @NotNull final HttpStatus status) {
        PagedModel<LinksUser> UserModels =
                pagedUserResourcesAssembler
                        .toModel(users, userAssembler);
        return ResponseEntity.status(status)
                .body(UserModels);
    }

    public ResponseEntity<LinksTemplate> addTemplateLinks(
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

    public ResponseEntity<PagedModel<LinksTemplate>> addPagedTemplateLinks(
            @NotNull final Page<Template> templates,
            @NotNull final HttpStatus status) {
        PagedModel<LinksTemplate> templateModels =
                pagedTemplateResourcesAssembler
                        .toModel(templates, templateAssembler);
        return ResponseEntity.status(status)
                .body(templateModels);
    }

}
