
package no.kdrs.grouse.controller;

import no.kdrs.grouse.service.ACLService;
import no.kdrs.grouse.utils.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
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

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER)
    public ResponseEntity<LinksAccessControl>
    getACLEntry(@PathVariable(OBJECT_ID) UUID objectId) {
        return commonController.addACLLinks(
                aclService.getACLEntry(objectId), OK);
    }

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER)
    public ResponseEntity<LinksAccessControl>
    createACLEntry(@PathVariable(OBJECT_ID) UUID objectId) {
        return commonController.addACLLinks(
                aclService.createACLEntry(objectId), OK);
    }

    @GetMapping(value = SLASH + FUNCTIONALITY_PARAMETER)
    public ResponseEntity<LinksAccessControl>
    deleteACLEntry(@PathVariable(OBJECT_ID) UUID objectId) {
        return commonController.addACLLinks(
                aclService.deleteACLEntry(objectId), OK);
    }
}
