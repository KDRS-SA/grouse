
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.model.links.LinksProjectFunctionality;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Created by tsodring on 9/25/17.
 */
@RestController
@RequestMapping(value = SLASH + PROJECT)
public class ProjectController {

    private static final Logger logger =
            LoggerFactory.getLogger(ProjectController.class);

    private IProjectService projectService;
    private CommonController commonController;

    public ProjectController(
            IProjectService projectService,
            CommonController commonController) {
        this.projectService = projectService;
        this.commonController = commonController;
    }

    @GetMapping(value = SLASH + PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<LinksProject> getProject(
            @PathVariable(PROJECT_NUMBER) Long projectId) {
        return commonController.addProjectLinks(projectService
                .findById(projectId), OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<LinksProject>>
    getProjects(Pageable pageable) {
        return commonController.addPagedProjectLinks(projectService
                .findAll(pageable), OK);
    }

    @GetMapping(value = SLASH + PROJECT_NUMBER_PARAMETER + SLASH +
            FUNCTIONALITY)
    public ResponseEntity<PagedModel<LinksProjectFunctionality>>
    getFunctionalityForProject(
            Pageable pageable,
            @PathVariable(PROJECT_NUMBER) Long projectId) {
        return commonController.addPagedProjectFunctionalityLinks(
                projectService.findFunctionalityForProjectByType(
                        pageable, projectId, "mainmenu"), OK);
    }

    @PatchMapping(value = SLASH + PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<LinksProject> patchRequirement(
            @PathVariable(PROJECT_NUMBER) Long projectId,
            @RequestBody PatchObjects patchObjects) {
        return commonController.addProjectLinks(projectService.update(
                projectId, patchObjects), OK);
    }

    @PostMapping
    public ResponseEntity<LinksProject> createProject(
            @RequestBody Project project) {
        return commonController.addProjectLinks(
                projectService.createProject(project), CREATED);
    }

    @DeleteMapping(SLASH + PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<Void> deleteProject(
            @PathVariable(PROJECT_NUMBER) Long projectId) {
        projectService.delete(projectId);
        return ResponseEntity.status(NO_CONTENT)
                .body(null);
    }
}
