
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.APIDetail;
import no.kdrs.grouse.model.APIDetails;
import no.kdrs.grouse.service.interfaces.IGrouseUserService;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by tsodring on 24/06/18.
 */
@RestController
@RequestMapping(value = SLASH )
public class ApplicationController {

    private IGrouseUserService grouseUserService;

    public ApplicationController(IGrouseUserService grouseUserService) {
        this.grouseUserService = grouseUserService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<APIDetails> getApplicationDetails() throws Exception {

        APIDetails apiDetails = new APIDetails();

        Link link = linkTo(methodOn(UserController.class).saveGrouseUser(null)).withRel("user");
        APIDetail createAccountDetail = new APIDetail(new String(link.getHref()), REL_USER, false);

        link = linkTo(methodOn(TokenEndpoint.class).postAccessToken(null, null)).withRel("login");
        APIDetail loginDetail = new APIDetail(link.getHref(), REL_LOGIN_OAUTH, false);

        link = linkTo(methodOn(OAuthController.class).logout(null)).withRel("logout");
        APIDetail logoutDetail = new APIDetail(link.getHref(), REL_LOGOUT_OAUTH, false);

        apiDetails.addApiDetal(createAccountDetail);
        apiDetails.addApiDetal(loginDetail);
        apiDetails.addApiDetal(logoutDetail);

        apiDetails.add(linkTo(methodOn
                (ApplicationController.class).
                getApplicationDetails()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).
                body(apiDetails);
    }

}
