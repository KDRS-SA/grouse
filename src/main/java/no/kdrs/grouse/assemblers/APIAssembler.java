package no.kdrs.grouse.assemblers;

import no.kdrs.grouse.controller.*;
import no.kdrs.grouse.model.APIDetail;
import no.kdrs.grouse.model.links.LinksAPIDetail;
import no.kdrs.grouse.utils.RoleValidator;
import no.kdrs.grouse.utils.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class APIAssembler
        extends
        RepresentationModelAssemblerSupport<APIDetail, LinksAPIDetail> {

    private static final Logger logger =
            LoggerFactory.getLogger(APIAssembler.class);
    private final RoleValidator role;

    public APIAssembler(RoleValidator role) {
        super(ApplicationController.class, LinksAPIDetail.class);
        this.role = role;
    }

    @Override
    public LinksAPIDetail toModel(APIDetail aPIDetail) {
        LinksAPIDetail linksAPIDetail = instantiateModel(aPIDetail);

        try {
            linksAPIDetail.add(linkTo(methodOn(TokenEndpoint.class)
                    .postAccessToken(null, null))
                    .withRel(REL_LOGIN_OAUTH));
            linksAPIDetail.add(linkTo(methodOn(UserController.class)
                    .saveGrouseUser(null))
                    .withRel(REL_CREATE_USER));
        } catch (HttpRequestMethodNotSupportedException e) {
            logger.error(e.getMessage());
            throw new InternalException(e.getMessage());
        }

        // Print the following if the user is logged in
        String username = getUser();
        if (null != username) {
            linksAPIDetail.add(linkTo(methodOn(UserController.class)
                    .getGrouseUser(username))
                    .withRel(REL_USER));
            linksAPIDetail.add(linkTo(methodOn(ProjectController.class)
                    .getProjectsForUser(null))
                    .withRel(REL_PROJECT_LIST));
            linksAPIDetail.add(linkTo(methodOn(DocumentController.class)
                    .getSupportedFileFormats())
                    .withRel(SUPPORTED_FORMATS));
            linksAPIDetail.add(linkTo(methodOn(TemplateController.class)
                    .getTemplates(null))
                    .withRel(REL_TEMPLATE_LIST));
            linksAPIDetail.add(linkTo(methodOn(TemplateController.class)
                    .createTemplate(null))
                    .withRel(TEMPLATE));
            linksAPIDetail.add(linkTo(methodOn(OAuthController.class)
                    .logout(null))
                    .withRel(REL_LOGOUT_OAUTH));
            // Print the following if the user is admin
            if (role.isAdmin()) {
                linksAPIDetail.add(linkTo(methodOn(ProjectController.class)
                        .getProjects(null))
                        .withRel(REL_PROJECT_LIST_ALL));
                linksAPIDetail.add(linkTo(methodOn(TemplateController.class)
                        .getTemplates(null))
                        .withRel(REL_TEMPLATE_LIST_ALL));
            }
        }
        return linksAPIDetail;
    }

    /**
     * if there is no logged in user
     * if (!(context.getAuthentication()
     * instanceof AnonymousAuthenticationToken))
     * then return null. Not returning anonymousUser as we want to be able to
     * actually know if there is a user.
     *
     * @return the username or null of noone is logged in
     */
    private String getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            if (!(context.getAuthentication()
                    instanceof AnonymousAuthenticationToken)) {
                return context.getAuthentication().getName();
            } else {
                return null;
            }
        }
        throw new InternalException("Unable to find security context in " +
                "ApplicationController::getUser()");
    }
}
