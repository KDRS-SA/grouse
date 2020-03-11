
package no.kdrs.grouse.controller;

import no.kdrs.grouse.assemblers.APIAssembler;
import no.kdrs.grouse.model.APIDetail;
import no.kdrs.grouse.model.links.LinksAPIDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static no.kdrs.grouse.utils.Constants.SLASH;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by tsodring on 24/06/18.
 */
@RestController
@RequestMapping(value = SLASH)
public class ApplicationController {

    private static final Logger logger =
            LoggerFactory.getLogger(ApplicationController.class);

    APIAssembler apiAssembler;

    public ApplicationController(APIAssembler apiAssembler) {
        this.apiAssembler = apiAssembler;
    }

    @GetMapping
    public ResponseEntity<LinksAPIDetail> getApplicationDetails() {
        return ResponseEntity.status(OK).
                body(apiAssembler.toModel(new APIDetail()));
    }
}
