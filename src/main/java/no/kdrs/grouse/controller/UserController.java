
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.model.links.LinksUser;
import no.kdrs.grouse.service.interfaces.IGrouseUserService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.HttpStatus.*;

/**
 * Created by tsodring on 28/03/18.
 */
@RestController
@RequestMapping(value = SLASH + USER, produces = HAL_JSON_VALUE)
public class UserController {

    private IGrouseUserService grouseUserService;
    private IProjectService projectService;
    private CommonController commonController;

    public UserController(
            IGrouseUserService grouseUserService,
            IProjectService projectService,
            CommonController commonController) {
        this.grouseUserService = grouseUserService;
        this.projectService = projectService;
        this.commonController = commonController;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<LinksUser>> getGrouseUsers(
            Pageable pageable) {
        return commonController.addPagedUserLinks(
                grouseUserService.findAll(pageable), OK);
    }

    @GetMapping(value = SLASH + "{" + USER + ":.+}")
    public ResponseEntity<LinksUser> getGrouseUser(
            @PathVariable(USER) String username) {
        commonController.checkAccess(username);
        return commonController.addUserLinks(
                grouseUserService.findById(username), OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = SLASH + USER_PARAMETER + SLASH + REL_PROJECT_USER_LIST)
    public ResponseEntity<PagedModel<LinksProject>>
    getGrouseUserProjectsForAdmin(
            Pageable pageable,
            @PathVariable(USER) String username) {
        return commonController.addPagedProjectLinks(
                projectService.findAllForUser(username, pageable), OK);
    }

    @PostMapping
    public ResponseEntity<LinksUser> saveGrouseUser(
            @RequestBody GrouseUser user) {
        return commonController.addUserLinks(grouseUserService.save(user),
                CREATED);
    }

    @PatchMapping(value = SLASH + USER_PARAMETER)
    public ResponseEntity<GrouseUser> updateGrouseUser(
            @PathVariable(USER) String username,
            @RequestBody PatchObjects patchObjects) {
        commonController.checkAccess(username);
        return ResponseEntity.status(OK)
                .body(grouseUserService.update(username, patchObjects));
    }

    @GetMapping(value = SLASH + USER_PARAMETER + SLASH + PROJECT)
    public ResponseEntity<String> getGrouseUserProjects(
            @PathVariable(USER) String username) {
        commonController.checkAccess(username);
        projectService.findByOwnedBy(username)
                .forEach(p -> projectService.delete(p.getProjectId()));
        return ResponseEntity.status(NO_CONTENT)
                .body("{\"status\" : \"GrouseUser " + username +
                        " was deleted\"}");
    }

    @DeleteMapping(value = SLASH + USER_PARAMETER)
    public ResponseEntity<String> deleteGrouseUser(
            @PathVariable(USER) String username) {
        commonController.checkAccess(username);
        projectService.findByOwnedBy(username)
                .forEach(p -> projectService.delete(p.getProjectId()));
        grouseUserService.delete(username);
        return ResponseEntity.status(NO_CONTENT)
                .body("{\"status\" : \"GrouseUser " + username +
                        " was deleted\"}");
    }
}
