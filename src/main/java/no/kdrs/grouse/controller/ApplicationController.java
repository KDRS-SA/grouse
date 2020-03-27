
package no.kdrs.grouse.controller;

import no.kdrs.grouse.assemblers.APIAssembler;
import no.kdrs.grouse.model.APIDetail;
import no.kdrs.grouse.model.links.LinksAPIDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping
public class ApplicationController {

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
