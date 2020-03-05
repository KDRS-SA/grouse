
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.model.links.LinksUser;
import no.kdrs.grouse.service.interfaces.IGrouseUserService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.utils.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by tsodring on 28/03/18.
 */
@RestController
@RequestMapping(value = SLASH + USER)
public class UserController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

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

    @GetMapping(value = SLASH + USER_PARAMETER)
    public ResponseEntity<LinksUser> getGrouseUser(
            @PathVariable(USER) String username) {
        commonController.checkAccess(username);
        return commonController.addUserLinks(
                grouseUserService.findById(username), OK);
    }

    @GetMapping(value = SLASH + USER_PARAMETER + SLASH + PROJECT)
    public ResponseEntity<PagedModel<LinksProject>> getGrouseUserProjects(
            Pageable pageable,
            @PathVariable(USER) String username) {
        commonController.checkAccess(username);
        return commonController.addPagedProjectLinks(
                projectService.findByOwnedBy(username, pageable), OK);
    }

    @PostMapping
    public ResponseEntity<LinksUser> saveGrouseUser(
            @RequestBody GrouseUser user) {
        return commonController.addUserLinks(grouseUserService.save(user),
                CREATED);
    }

    @PutMapping(value = SLASH + USER_PARAMETER)
    public ResponseEntity<GrouseUser> updateGrouseUser(
            @PathVariable(USER) String username,
            @RequestBody GrouseUser user) throws EntityNotFoundException {
        commonController.checkAccess(username);
        return ResponseEntity.status(OK)
                .body(grouseUserService.update(username, user));
    }

    @DeleteMapping(value = USER_PARAMETER)
    public ResponseEntity<String> deleteGrouseUser(
            @PathVariable String username) {
        commonController.checkAccess(username);
        projectService.findByOwnedBy(username)
                .forEach(p -> projectService.delete(p.getProjectId()));
        grouseUserService.delete(username);
        return ResponseEntity.status(OK)
                .body("GrouseUser with username " + username +
                        " was deleted");
    }
}
