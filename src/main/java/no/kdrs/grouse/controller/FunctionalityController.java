package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Functionality;
import no.kdrs.grouse.service.interfaces.IFunctionalityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.FUNCTIONALITY;
import static no.kdrs.grouse.utils.Constants.SLASH;

/**
 * Created by tsodring on 9/25/17.
 */
@RestController
@RequestMapping(value = SLASH + FUNCTIONALITY)
public class FunctionalityController {

    private IFunctionalityService functionalityService;

    FunctionalityController(IFunctionalityService functionalityService) {
        this.functionalityService = functionalityService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Functionality>> getFunctionalities() {
        return new ResponseEntity<>
                (functionalityService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/meny/{parent:.+}"
    )
    public ResponseEntity<List<Functionality>> getFunctionalityForMenu(
            @PathVariable("parent") String parent) {
        return new ResponseEntity<>(functionalityService
                .findByShowMeAndReferenceParentFunctionality(
                        true, parent), HttpStatus.OK);
    }

    @RequestMapping(value = "/{funksjon:.+}", method = RequestMethod.GET)
    public ResponseEntity<Functionality>
    getFunctionality(@PathVariable("funksjon") String functionalityNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(functionalityService.findByFunctionalityNumber
                        (functionalityNumber));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Functionality> saveFunctionality(
            @RequestBody Functionality Functionality) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(functionalityService.save(Functionality));
    }

    @RequestMapping(value = "/{funksjon:.+}", method = RequestMethod.PUT)
    public ResponseEntity<Functionality> updateFunctionality(
            @PathVariable("funksjon") Long id,
            @RequestBody Functionality functionality)
            throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(functionalityService.update(
                        id, functionality));
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFunctionality(@PathVariable Long id) {
        functionalityService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Functionality with id " + id + " was deleted");
    }
}
