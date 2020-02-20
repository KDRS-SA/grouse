package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.service.interfaces.ITemplateFunctionalityService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static no.kdrs.grouse.utils.Constants.FUNCTIONALITY;
import static no.kdrs.grouse.utils.Constants.SLASH;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by tsodring on 9/25/17.
 */
@RestController
@RequestMapping(value = SLASH + FUNCTIONALITY)
public class FunctionalityController {

    private ITemplateFunctionalityService functionalityService;

    FunctionalityController(ITemplateFunctionalityService functionalityService) {
        this.functionalityService = functionalityService;
    }

    @GetMapping
    public ResponseEntity<List<TemplateFunctionality>> getFunctionalities() {
        return new ResponseEntity<>
                (functionalityService.findAll(), OK);
    }

    @GetMapping(value = "/meny/{parent:.+}")
    public ResponseEntity<List<TemplateFunctionality>> getFunctionalityForMenu(
            @PathVariable("parent") String parent) {
        return new ResponseEntity<>(functionalityService
                .findByShowMeAndReferenceParentFunctionality(
                        true, parent), OK);
    }

    @GetMapping(value = "/{funksjon:.+}")
    public ResponseEntity<TemplateFunctionality>
    getFunctionality(@PathVariable("funksjon") String functionalityNumber) {
        return ResponseEntity.status(OK)
                .body(functionalityService.findByFunctionalityNumber
                        (functionalityNumber));
    }

    @PostMapping
    public ResponseEntity<TemplateFunctionality> saveFunctionality(
            @RequestBody TemplateFunctionality TemplateFunctionality) {
        return ResponseEntity.status(CREATED)
                .body(functionalityService.save(TemplateFunctionality));
    }


    @PatchMapping(value = "/{funksjon:.+}")
    public ResponseEntity<TemplateFunctionality> updateFunctionality(
            @PathVariable("funksjon") Long id,
            @RequestBody PatchObjects patchObjects)
            throws Exception {
        TemplateFunctionality templateFunctionality = functionalityService
                .updateProjectFunctionality(patchObjects, id);
        return ResponseEntity.status(OK)
                .eTag(templateFunctionality.getVersion().toString())
                .body(templateFunctionality);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteFunctionality(@PathVariable Long id) {
        functionalityService.delete(id);
        return ResponseEntity.status(OK)
                .body("TemplateFunctionality with id " + id + " was deleted");
    }
}
