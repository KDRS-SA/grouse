
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.service.interfaces.IGrouseUserService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by tsodring on 28/03/18.
 */
@RestController
@RequestMapping(value = SLASH + USER)
public class UserController {

    private IGrouseUserService grouseUserService;
    private IProjectService projectService;

    public UserController(IGrouseUserService grouseUserService,
                          IProjectService projectService) {
        this.grouseUserService = grouseUserService;
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<GrouseUser>> getGrouseUsers() {
        return ResponseEntity.status(OK).
                body(grouseUserService.findAll());
    }

    @GetMapping(value = "/{" + USER + "}")
    public ResponseEntity<GrouseUser> getGrouseUser(
            @PathVariable(USER) String username)
    throws Exception {
        GrouseUser user = grouseUserService.findById(username);
        user.add(linkTo(methodOn(UserController.class).
                getGrouseUser(username)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).
                createProject(username, null)).withRel(PROJECT));

        return ResponseEntity.status(OK)
                .body(user);
    }

    @GetMapping(value = "/{" + USER + "}/" + PROJECT)
    public ResponseEntity<List<Project>> getGrouseUserProjects(
            @PathVariable(USER) String username) throws Exception {

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        if (!loggedInUser.equals(username)) {
            throw new AccessDeniedException("Du er pålogget med en bruker som ikke har tilgang til dette prosjektet!");
        }
        List<Project> projects = projectService.findByOwnedBy(username);
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

        return ResponseEntity.status(OK)
                .body(projects);
    }

    @PostMapping(value = "/{" + USER + "}/" + PROJECT)
    public ResponseEntity<Project> createProject(
            @PathVariable(USER) String ownedBy,
            @RequestBody Project project) throws Exception {

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        if (!loggedInUser.equals(ownedBy)) {
            throw new AccessDeniedException("Du er pålogget med en bruker som ikke har tilgang til dette prosjektet!");
        }

        projectService.createProject(project);

        project.add(linkTo(methodOn(ProjectController.class).
                getProject(project.getProjectId())).withSelfRel());

        project.add(linkTo(methodOn(ProjectController.class).
                getFunctionalityForProject(project.getProjectId()))
                .withRel(FUNCTIONALITY));

        project.add(linkTo(DocumentController.class, DocumentController.class.
                getMethod("downloadDocument", Long.class,
                        HttpServletResponse.class), project.getProjectId()).
                withRel(DOCUMENT));

        return ResponseEntity.status(OK)
                .body(project);
    }

    @PostMapping
    public ResponseEntity<GrouseUser> saveGrouseUser(
            @RequestBody GrouseUser user) {
        return ResponseEntity.status(CREATED)
                .body(grouseUserService.save(user));
    }

    @PutMapping(value = "/{" + USER + "}")
    public ResponseEntity<GrouseUser> updateGrouseUser(
            @PathVariable(USER) String username,
            @RequestBody GrouseUser user) throws EntityNotFoundException {
        return ResponseEntity.status(OK)
                .body(grouseUserService.update(username, user));
    }

    @DeleteMapping(value = "/{" + USER + "}")
    public ResponseEntity<String> deleteGrouseUser(
            @PathVariable String username) {
        projectService.findByOwnedBy(username)
                .forEach(p -> projectService.delete(p.getProjectId()));
        grouseUserService.delete(username);
        return ResponseEntity.status(OK)
                .body("GrouseUser with username " + username +
                        " was deleted");
    }
}
