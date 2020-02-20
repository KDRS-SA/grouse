package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.service.interfaces.ITemplateRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static no.kdrs.grouse.utils.Constants.REQUIREMENT;
import static no.kdrs.grouse.utils.Constants.SLASH;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
/**
 * Created by tsodring on 9/25/17.
 */
@RestController
@RequestMapping(value = SLASH + REQUIREMENT)
public class RequirementController {

    private ITemplateRequirementService requirementService;

    RequirementController(ITemplateRequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @GetMapping
    public ResponseEntity<List<TemplateRequirement>> getRequirements() {
        return ResponseEntity.status(OK).
                body(requirementService.findAll());
    }

    @GetMapping(value = "/{krav:.+}")
    public ResponseEntity<TemplateRequirement> getRequirement(
            @PathVariable("krav") Long requirementNumber) {
        return ResponseEntity.status(OK)
                .body(requirementService.
                        findById(requirementNumber));
    }

    @PostMapping
    public ResponseEntity<TemplateRequirement> saveRequirement(
            @RequestBody TemplateRequirement templateRequirement) {
        return ResponseEntity.status(CREATED)
                .eTag(templateRequirement.getVersion().toString())
                .body(requirementService.save(templateRequirement));
    }

    @PatchMapping(value = "/{krav}")
    public ResponseEntity<TemplateRequirement> patchRequirement(
            @PathVariable("krav") Long requirementNumber,
            @RequestBody PatchObjects patchObjects) {
        TemplateRequirement templateRequirement = requirementService
                .updateTemplateRequirement(patchObjects, requirementNumber);
        return ResponseEntity.status(OK)
                .eTag(templateRequirement.getVersion().toString())
                .body(templateRequirement);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteRequirement(@PathVariable Long id) {
        requirementService.delete(id);
        return ResponseEntity.status(OK)
                .body("TemplateRequirement with id " + id + " was deleted");
    }
}
