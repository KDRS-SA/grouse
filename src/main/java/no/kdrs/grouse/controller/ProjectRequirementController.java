
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.service.interfaces.IProjectRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by tsodring on 29/03/18.
 */
@RestController
@RequestMapping(value = SLASH + PROJECT_REQUIREMENT)
public class ProjectRequirementController {

    private IProjectRequirementService projectRequirementService;

    public ProjectRequirementController(
            IProjectRequirementService projectRequirementService) {
        this.projectRequirementService = projectRequirementService;
    }

    @GetMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<ProjectRequirement> getRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {

        ProjectRequirement projectRequirement = projectRequirementService.
                getProjectRequirement(requirementNumber);

        projectRequirement.add(linkTo(methodOn
                (ProjectRequirementController.class).
                getRequirement(projectRequirement.getRequirementId())).
                withSelfRel());

        return ResponseEntity.status(OK)
                .body(projectRequirement);
    }

    @PatchMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<ProjectRequirement> patchRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber,
            @RequestBody PatchObjects patchObjects)
                throws Exception {

        ProjectRequirement projectRequirement = projectRequirementService.
                updateProjectRequirement(patchObjects, requirementNumber);

        projectRequirement.add(linkTo(methodOn
                (ProjectRequirementController.class).
                getRequirement(projectRequirement.getRequirementId())).
                withSelfRel());

        return ResponseEntity.status(OK)
                .body(projectRequirement);
    }

    @PostMapping(value = "/{prosjekt}/funksjonalitet/{funksjonalitet}")
    public ResponseEntity<ProjectRequirement> createRequirement(
            @PathVariable("prosjekt") Long projectId,
            @PathVariable("funksjonalitet") String functionality,
            @RequestBody ProjectRequirement projectRequirement) {

        projectRequirementService.
                createProjectRequirement(projectId, functionality,
                        projectRequirement);

        projectRequirement.add(linkTo(methodOn
                (ProjectRequirementController.class).
                getRequirement(projectRequirement.getRequirementId())).
                withSelfRel());

        return ResponseEntity.status(CREATED)
                .body(projectRequirement);
    }


    @DeleteMapping(value = SLASH + REQUIREMENT_PARAMETER)
    public ResponseEntity<String>
    deleteProjectRequirement(
            @PathVariable(REQUIREMENT) Long requirementNumber) {
        projectRequirementService.
                deleteProjectRequirement(requirementNumber);
        return ResponseEntity.status(OK).body("{\"result\":\"OK\" " +
                "}");
    }
}
