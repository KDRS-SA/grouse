
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.service.interfaces.IDocumentService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by tsodring on 9/25/17.
 * <p>
 * Controller that has two tasks.
 * <p>
 * 1. Call for the creation of a requirements document
 * 2. Download the requirements document
 */
@RestController
@RequestMapping(value = SLASH + DOCUMENT)
public class DocumentController {

    private IDocumentService documentService;
    private IProjectService projectService;

    @Value("${storage.location}")
    private String directoryPath;

    public DocumentController(IDocumentService documentService,
                              IProjectService projectService) {
        this.documentService = documentService;
        this.projectService = projectService;
    }

    @PostMapping(value = PROJECT_NUMBER_PARAMETER)
    public ResponseEntity<Project> getRequirement(
            @PathVariable("prosjektnummer") Long projectId)
            throws Exception {

        Project project = projectService.findById(projectId);
        documentService.createDocument(project);
        project.setChangedDate(new Date());
        project.setDocumentCreated(true);
        project.setProjectComplete(true);
        projectService.update(projectId, project);

        project.add(linkTo(methodOn(ProjectController.class).
                getProject(projectId)).withSelfRel());

        project.add(linkTo(methodOn(ProjectController.class).
                getFunctionalityForProject(projectId))
                .withRel(FUNCTIONALITY));

        project.add(linkTo(DocumentController.class, DocumentController.class.
                getMethod("downloadDocument", Long.class,
                        HttpServletResponse.class), projectId).
                withRel(DOCUMENT));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(project);
    }

    @GetMapping(PROJECT_NUMBER_PARAMETER)
    public HttpEntity<byte[]> downloadDocument(
            @PathVariable("prosjektnummer") Long projectId,
                HttpServletResponse response)
            throws IOException {

        Project project = projectService.findById(projectId);

        if (null != project.getFileNameInternal()) {
            // throw an exception
        }

        Path file = Paths.get(project.getFileNameInternal());
        Resource resource = new UrlResource(file.toUri());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        String header = "Content-Disposition";
        String value = "\"attachment; filename=\"" + project.getFileName()+"\"";
        responseHeaders.add(header, value);


        byte[] documentBody = new byte[(int)Files.size(file)];
        IOUtils.readFully(resource.getInputStream(),
                documentBody);

        return  new HttpEntity<byte[]>(documentBody, responseHeaders);
    }
}
