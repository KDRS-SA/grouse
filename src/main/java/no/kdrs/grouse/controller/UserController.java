
package no.kdrs.grouse.controller;

import no.kdrs.grouse.assemblers.UserAssembler;
import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.links.LinksUser;
import no.kdrs.grouse.service.interfaces.IGrouseUserService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
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
public class UserController
        extends GrouseController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    private IGrouseUserService grouseUserService;
    private IProjectService projectService;
    private PagedResourcesAssembler<GrouseUser> pagedResourcesAssembler;
    private UserAssembler userAssembler;

    public UserController(
            IGrouseUserService grouseUserService,
            IProjectService projectService,
            PagedResourcesAssembler<GrouseUser> pagedResourcesAssembler,
            UserAssembler userAssembler) {
        this.grouseUserService = grouseUserService;
        this.projectService = projectService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.userAssembler = userAssembler;
    }

    @GetMapping
    public ResponseEntity<List<GrouseUser>> getGrouseUsers() {
        return ResponseEntity.status(OK).
                body(grouseUserService.findAll());
    }

    @GetMapping(value = SLASH + USER_PARAMETER)
    public ResponseEntity<LinksUser> getGrouseUser(
            @PathVariable(USER) String username) {
        checkAccess(username);
        return addUserLinks(grouseUserService.findById(username), OK);
    }

    @GetMapping(value = SLASH + USER_PARAMETER + SLASH + PROJECT)
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
                    getMethod("downloadDocumentProject", Long.class,
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

    @PostMapping(value = SLASH + USER_PARAMETER + SLASH + PROJECT)
    public ResponseEntity<Project> createProject(
            @PathVariable(USER) String ownedBy,
            @RequestBody Project project) {

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

        project.add(linkTo(DocumentController.class,
                methodOn(DocumentController.class)
                        .downloadProjectDocument(project.getProjectId()))
                .withRel(DOCUMENT));
        return ResponseEntity.status(OK)
                .body(project);
    }

    @PostMapping
    public ResponseEntity<LinksUser> saveGrouseUser(
            @RequestBody GrouseUser user) {
        return addUserLinks(grouseUserService.save(user), CREATED);
    }

    @PutMapping(value = SLASH + USER_PARAMETER)
    public ResponseEntity<GrouseUser> updateGrouseUser(
            @PathVariable(USER) String username,
            @RequestBody GrouseUser user) throws EntityNotFoundException {
        checkAccess(username);
        return ResponseEntity.status(OK)
                .body(grouseUserService.update(username, user));
    }

    @DeleteMapping(value = USER_PARAMETER)
    public ResponseEntity<String> deleteGrouseUser(
            @PathVariable String username) {
        checkAccess(username);
        projectService.findByOwnedBy(username)
                .forEach(p -> projectService.delete(p.getProjectId()));
        grouseUserService.delete(username);
        return ResponseEntity.status(OK)
                .body("GrouseUser with username " + username +
                        " was deleted");
    }

    private ResponseEntity<LinksUser> addUserLinks(
            @NotNull final GrouseUser user,
            @NotNull final HttpStatus status) {
        return ResponseEntity.status(status)
                .eTag(user.getVersion().toString())
                .body(userAssembler.toModel(user));
    }
}
