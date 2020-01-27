
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.service.interfaces.IProjectRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import static no.kdrs.grouse.utils.Constants.PROJECT_REQUIREMENT;
import static no.kdrs.grouse.utils.Constants.SLASH;


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

    @RequestMapping(value = "/{krav}", method = RequestMethod.GET)
    public ResponseEntity<ProjectRequirement> getRequirement(
            @PathVariable("krav") Long requirementNumber) {

        ProjectRequirement projectRequirement = projectRequirementService.
                getProjectRequirement(requirementNumber);

        projectRequirement.add(linkTo(methodOn
                (ProjectRequirementController.class).
                getRequirement(projectRequirement.getProjectRequirementId())).
                withSelfRel());

        return ResponseEntity.status(HttpStatus.OK)
                .body(projectRequirement);
    }

    @RequestMapping(value = "/{krav}", method = RequestMethod.PATCH)
    public ResponseEntity<ProjectRequirement> patchRequirement(
            @PathVariable("krav") Long requirementNumber,
            @RequestBody PatchObjects patchObjects)
                throws Exception {

        ProjectRequirement projectRequirement = projectRequirementService.
                updateProjectRequirement(patchObjects, requirementNumber);

        projectRequirement.add(linkTo(methodOn
                (ProjectRequirementController.class).
                getRequirement(projectRequirement.getProjectRequirementId())).
                withSelfRel());

        return ResponseEntity.status(HttpStatus.OK)
                .body(projectRequirement);
    }

    @RequestMapping(value = "/{prosjekt}/funksjonalitet/{funksjonalitet}",
            method = RequestMethod.POST)
    public ResponseEntity<ProjectRequirement> createRequirement(
            @PathVariable("prosjekt") Long projectId,
            @PathVariable("funksjonalitet") String functionality,
            @RequestBody ProjectRequirement projectRequirement) {

        projectRequirementService.
                createProjectRequirement(projectId, functionality,
                        projectRequirement);

        projectRequirement.add(linkTo(methodOn
                (ProjectRequirementController.class).
                getRequirement(projectRequirement.getProjectRequirementId())).
                withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectRequirement);
    }


    @RequestMapping(value ="/{krav}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String>
    deleteProjectRequirement(
            @PathVariable("krav") Long requirementNumber) {
                projectRequirementService.
                        deleteProjectRequirement(requirementNumber);
        return ResponseEntity.status(HttpStatus.OK).body("{\"result\":\"OK\" " +
                "}");
    }
}
