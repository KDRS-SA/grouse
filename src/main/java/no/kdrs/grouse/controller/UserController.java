
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.service.interfaces.IGrouseUserService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by tsodring on 28/03/18.
 */
@RestController
@RequestMapping(value = SLASH + USER)
public class UserController {

    private IGrouseUserService grouseUserService;
    private IProjectService projectService;
    //private EntityLinks entityLinks;

    public UserController(IGrouseUserService grouseUserService,
                          IProjectService projectService) {
        this.grouseUserService = grouseUserService;
        this.projectService = projectService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GrouseUser>> getGrouseUsers() {
        return ResponseEntity.status(HttpStatus.OK).
                body(grouseUserService.findAll());
    }

    @RequestMapping(value = "/{" + USER + "}",
            method = RequestMethod.GET)
    public ResponseEntity<GrouseUser> getGrouseUser(
            @PathVariable(USER) String username)
    throws Exception {
        GrouseUser user = grouseUserService.findById(username);
        user.add(linkTo(methodOn(UserController.class).
                getGrouseUser(username)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).
                createProject(username, null)).withRel(PROJECT));

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @RequestMapping(value = "/{" + USER + "}/" + PROJECT,
            method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getGrouseUserProjects(
            @PathVariable(USER) String username) throws Exception {

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        if (!loggedInUser.equals(username)) {
            throw new AccessDeniedException("Du er pålogget med en bruker som ikke har tilgang til dette prosjektet!");
        }
        GrouseUser user = new GrouseUser();
        user.setUsername(username);
        List<Project> projects = projectService.findByReferenceUser(user);
        for (Project project : projects) {
            project.add(linkTo(methodOn(ProjectController.class).
                    getProject(project.getProjectId())).withSelfRel());

            project.add(linkTo(methodOn(ProjectController.class).
                    getFunctionalityForProject(project.getProjectId()))
                    .withRel(FUNCTIONALITY));

            project.add(linkTo(DocumentController.class, DocumentController.class.
                    getMethod("downloadDocument", Long.class,
                            HttpServletResponse.class), project.getProjectId()).
                    withRel(DOCUMENT));
// Same for SRequirement
//            project.add(linkTo(methodOn(ProjectController.class).
//                    getRequirementsForFunctionality(project.getProjectId()))
//            .withRel(REQUIREMENT)            );
        }

        // entityLinks.linkToCollectionResource(UserController.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(projects);
    }

    @RequestMapping(value = "/{" + USER + "}/" + PROJECT,
            method = RequestMethod.POST)
    public ResponseEntity<Project> createProject(
            @PathVariable(USER) String username,
            @RequestBody Project project) throws Exception {
        GrouseUser user = new GrouseUser();
        user.setUsername(username);
        projectService.createProject_A(project);
        projectService.createProject_B(project);
        projectService.createProject_C(project);

        project.add(linkTo(methodOn(ProjectController.class).
                getProject(project.getProjectId())).withSelfRel());

        project.add(linkTo(methodOn(ProjectController.class).
                getFunctionalityForProject(project.getProjectId()))
                .withRel(FUNCTIONALITY));

        project.add(linkTo(DocumentController.class, DocumentController.class.
                getMethod("downloadDocument", Long.class,
                        HttpServletResponse.class), project.getProjectId()).
                withRel(DOCUMENT));

        return ResponseEntity.status(HttpStatus.OK)
                .body(project);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GrouseUser> saveGrouseUser(
            @RequestBody GrouseUser user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(grouseUserService.save(user));
    }

    @RequestMapping(value = "/{" + USER + "}",
            method = RequestMethod.PUT)
    public ResponseEntity<GrouseUser> updateGrouseUser(
            @PathVariable(USER) String username,
            @RequestBody GrouseUser user) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(grouseUserService.update(username, user));
    }

    @RequestMapping(value = "/{" + USER + "}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteGrouseUser(
            @PathVariable String username) {
        grouseUserService.delete(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body("GrouseUser with username " + username + " was deleted");
    }
}
