
package no.kdrs.grouse.controller;
import no.kdrs.grouse.model.Requirement;
import no.kdrs.grouse.service.IRequirementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static no.kdrs.grouse.utils.Constants.*;

/**
 * Created by tsodring on 9/25/17.
 */
@RestController
@RequestMapping(value = SLASH + REQUIREMENT)
public class RequirementController {

    private IRequirementService requirementService;

    RequirementController(IRequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<Requirement>> getRequirements(
            @RequestParam(value = REQUIREMENT_TYPE, required = false) String requirementType ) {

        Set<Requirement> requirements = new HashSet<>();

        if (requirementType == null) {
            requirements = requirementService.findAll();
        }
        else {
            requirementService.findByRequirementType(requirementType);
        }
        return ResponseEntity.status(HttpStatus.OK).body(requirements);
    }

    @RequestMapping(value = "/{krav:.+}", method = RequestMethod.GET)
    public ResponseEntity<Requirement> getRequirement(@PathVariable("krav") String requirementNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(requirementService.findByRequirementNumber(requirementNumber));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Requirement> saveRequirement(@RequestBody Requirement Requirement) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requirementService.save(Requirement));
    }

    @RequestMapping(value = "/{krav:.+}", method = RequestMethod.PUT)
    public ResponseEntity<Requirement> updateRequirement(
            @PathVariable("krav") String requirementId,
            @RequestBody Requirement requirement) throws Exception{
        return ResponseEntity.status(HttpStatus.OK)
                .body(requirementService.update(requirementId, requirement));
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRequirement(@PathVariable Long id) {
        requirementService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Requirement with id " + Long.toString(id) + " was deleted");
    }
}