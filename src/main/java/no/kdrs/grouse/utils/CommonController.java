package no.kdrs.grouse.utils;

import no.kdrs.grouse.assemblers.*;
import no.kdrs.grouse.model.*;
import no.kdrs.grouse.model.links.*;
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

import static no.kdrs.grouse.utils.Constants.NO_ACCESS_OTHER_USER;

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
    private PagedResourcesAssembler<TemplateRequirement>
            pagedTemplateRequirementResourcesAssembler;
    private PagedResourcesAssembler<ProjectFunctionality>
            pagedProjectFunctionalityResourcesAssembler;
    private PagedResourcesAssembler<ProjectRequirement>
            pagedProjectRequirementResourcesAssembler;
    private PagedResourcesAssembler<TemplateFunctionality>
            pagedTemplateFunctionalityResourcesAssembler;
    private ProjectAssembler projectAssembler;
    private TemplateAssembler templateAssembler;
    private ACLAssembler aclAssembler;
    private UserAssembler userAssembler;
    private TemplateRequirementAssembler templateRequirementAssembler;
    private ProjectFunctionalityAssembler projectFunctionalityAssembler;
    private ProjectRequirementAssembler projectRequirementAssembler;
    private TemplateFunctionalityAssembler templateFunctionalityAssembler;
    private SupportedFormatAssembler supportedFormatAssembler;

    public CommonController(
            RoleValidator role,
            PagedResourcesAssembler<GrouseUser> pagedUserResourcesAssembler,
            PagedResourcesAssembler<Template> pagedTemplateResourcesAssembler,
            PagedResourcesAssembler<TemplateRequirement>
                    pagedTemplateRequirementResourcesAssembler,
            PagedResourcesAssembler<TemplateFunctionality>
                    pagedTemplateFunctionalityResourcesAssembler,
            PagedResourcesAssembler<Project> pagedProjectResourcesAssembler,
            PagedResourcesAssembler<ProjectRequirement>
                    pagedProjectRequirementResourcesAssembler,
            PagedResourcesAssembler<ProjectFunctionality>
                    pagedProjectFunctionalityResourcesAssembler,
            ProjectAssembler projectAssembler,
            TemplateAssembler templateAssembler,
            ACLAssembler aclAssembler,
            UserAssembler userAssembler,
            ProjectRequirementAssembler projectRequirementAssembler,
            TemplateRequirementAssembler templateRequirementAssembler,
            ProjectFunctionalityAssembler projectFunctionalityAssembler,
            TemplateFunctionalityAssembler templateFunctionalityAssembler,
            SupportedFormatAssembler supportedFormatAssembler) {
        this.role = role;
        this.pagedUserResourcesAssembler = pagedUserResourcesAssembler;
        this.pagedTemplateResourcesAssembler = pagedTemplateResourcesAssembler;
        this.pagedTemplateRequirementResourcesAssembler =
                pagedTemplateRequirementResourcesAssembler;
        this.pagedTemplateFunctionalityResourcesAssembler =
                pagedTemplateFunctionalityResourcesAssembler;
        this.pagedProjectResourcesAssembler = pagedProjectResourcesAssembler;
        this.pagedProjectRequirementResourcesAssembler =
                pagedProjectRequirementResourcesAssembler;
        this.pagedProjectFunctionalityResourcesAssembler =
                pagedProjectFunctionalityResourcesAssembler;
        this.projectAssembler = projectAssembler;
        this.templateAssembler = templateAssembler;
        this.aclAssembler = aclAssembler;
        this.userAssembler = userAssembler;
        this.templateRequirementAssembler = templateRequirementAssembler;
        this.projectFunctionalityAssembler = projectFunctionalityAssembler;
        this.projectRequirementAssembler = projectRequirementAssembler;
        this.supportedFormatAssembler = supportedFormatAssembler;
        this.templateFunctionalityAssembler = templateFunctionalityAssembler;
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
        return ResponseEntity.status(status)
                .eTag(project.getVersion().toString())
                .body(projectAssembler.toModel(project));
    }

    public ResponseEntity<LinksUser> addUserLinks(
            @NotNull final GrouseUser user,
            @NotNull final HttpStatus status) {
        return ResponseEntity.status(status)
                .eTag(user.getVersion().toString())
                .body(userAssembler.toModel(user));
    }

    public ResponseEntity<LinksProjectFunctionality>
    addProjectFunctionalityLinks(
            @NotNull final ProjectFunctionality projectFunctionality,
            @NotNull final HttpStatus status) {
        return ResponseEntity.status(status)
                .eTag(projectFunctionality.getVersion().toString())
                .body(projectFunctionalityAssembler
                        .toModel(projectFunctionality));
    }

    public ResponseEntity<LinksTemplateFunctionality>
    addTemplateFunctionalityLinks(
            @NotNull final TemplateFunctionality templateFunctionality,
            @NotNull final HttpStatus status) {
        return ResponseEntity.status(status)
                .eTag(templateFunctionality.getVersion().toString())
                .body(templateFunctionalityAssembler
                        .toModel(templateFunctionality));
    }

    public ResponseEntity<LinksProjectRequirement>
    addProjectRequirementLinks(
            @NotNull final ProjectRequirement projectRequirement,
            @NotNull final HttpStatus status) {
        return ResponseEntity.status(status)
                .eTag(projectRequirement.getVersion().toString())
                .body(projectRequirementAssembler.toModel(projectRequirement));
    }

    public ResponseEntity<LinksTemplateRequirement>
    addTemplateRequirementLinks(
            @NotNull final TemplateRequirement templateRequirement,
            @NotNull final HttpStatus status) {
        return ResponseEntity.status(status)
                .eTag(templateRequirement.getVersion().toString())
                .body(templateRequirementAssembler
                        .toModel(templateRequirement));
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
        return ResponseEntity.status(status)
                .eTag(template.getVersion().toString())
                .body(templateAssembler.toModel(template));
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

    public ResponseEntity<PagedModel<LinksTemplateRequirement>>
    addPagedTemplateRequirementLinks(
            @NotNull final Page<TemplateRequirement> templateRequirements,
            @NotNull final HttpStatus status) {
        PagedModel<LinksTemplateRequirement> templateRequirementModels =
                pagedTemplateRequirementResourcesAssembler
                        .toModel(templateRequirements,
                                templateRequirementAssembler);
        return ResponseEntity.status(status)
                .body(templateRequirementModels);
    }

    public ResponseEntity<PagedModel<LinksProjectRequirement>>
    addPagedProjectRequirementLinks(
            @NotNull final Page<ProjectRequirement> projectRequirements,
            @NotNull final HttpStatus status) {
        PagedModel<LinksProjectRequirement> projectRequirementModels =
                pagedProjectRequirementResourcesAssembler
                        .toModel(projectRequirements,
                                projectRequirementAssembler);
        return ResponseEntity.status(status)
                .body(projectRequirementModels);
    }

    public ResponseEntity<PagedModel<LinksProjectFunctionality>>
    addPagedProjectFunctionalityLinks(
            @NotNull final Page<ProjectFunctionality> projectFunctionalitys,
            @NotNull final HttpStatus status) {
        PagedModel<LinksProjectFunctionality> projectFunctionalityModels =
                pagedProjectFunctionalityResourcesAssembler
                        .toModel(projectFunctionalitys,
                                projectFunctionalityAssembler);
        return ResponseEntity.status(status)
                .body(projectFunctionalityModels);
    }

    public ResponseEntity<PagedModel<LinksTemplateFunctionality>>
    addPagedTemplateFunctionalityLinks(
            @NotNull final Page<TemplateFunctionality> templateFunctionalitys,
            @NotNull final HttpStatus status) {
        PagedModel<LinksTemplateFunctionality> templateFunctionalityModels =
                pagedTemplateFunctionalityResourcesAssembler
                        .toModel(templateFunctionalitys,
                                templateFunctionalityAssembler);
        return ResponseEntity.status(status)
                .body(templateFunctionalityModels);
    }

    public ResponseEntity<LinksAccessControl>
    addACLLinks(AccessControl aclEntry, HttpStatus status) {
        return ResponseEntity.status(status)
                .eTag(aclEntry.getVersion().toString())
                .body(aclAssembler.toModel(aclEntry));
    }

    public ResponseEntity<LinksSupportedFormats>
    addSupportedFormatsLinks(SupportedFormats supportedFormats,
                             HttpStatus status) {
        return ResponseEntity.status(status)
                .body(supportedFormatAssembler.toModel(supportedFormats));
    }
}
