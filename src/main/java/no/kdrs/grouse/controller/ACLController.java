
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.AccessControl;
import no.kdrs.grouse.model.links.LinksAccessControl;
import no.kdrs.grouse.service.ACLService;
import no.kdrs.grouse.utils.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = SLASH + ACCESS_CONTROL)
public class ACLController {

    private ACLService aclService;
    private CommonController commonController;

    public ACLController(ACLService aclService,
                         CommonController commonController) {
        this.aclService = aclService;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + ACCESS_CONTROL_PARAMETER)
    public ResponseEntity<LinksAccessControl>
    getACLEntry(@PathVariable(OBJECT_ID) UUID objectId) {
        return commonController.addACLLinks(
                aclService.getACLEntry(objectId), OK);
    }

    @PostMapping(value = SLASH + ACCESS_CONTROL_PARAMETER)
    public ResponseEntity<LinksAccessControl>
    createACLEntry(@PathVariable(OBJECT_ID) UUID objectId,
                   @RequestBody AccessControl accessControl) {
        return commonController.addACLLinks(
                aclService.createACLEntry(objectId, accessControl), OK);
    }

    @DeleteMapping(value = SLASH + ACCESS_CONTROL_PARAMETER)
    public ResponseEntity<Void>
    deleteACLEntry(@PathVariable(OBJECT_ID) UUID objectId) {
        aclService.deleteACLEntry(objectId);
        return ResponseEntity.status(NO_CONTENT)
                .body(null);
    }
}
