
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.service.interfaces.IDocumentService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import no.kdrs.grouse.utils.exception.InternalException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import static org.springframework.http.HttpStatus.CREATED;
/**
 * Created by tsodring on 9/25/17.
 * <p>
 * Controller that has two tasks.
 * <p>
 * 1. Call for the creation of a requirements document
 * 2. Download the requirements document
 */
@RestController
@RequestMapping
public class DocumentController {

    private static final Logger logger =
            LoggerFactory.getLogger(DocumentController.class);

    private IDocumentService documentService;
    private IProjectService projectService;
    private ITemplateService templateService;

    public DocumentController(IDocumentService documentService,
                              IProjectService projectService,
                              ITemplateService templateService) {
        this.documentService = documentService;
        this.projectService = projectService;
        this.templateService = templateService;
    }

    @PostMapping(value = SLASH + PROJECT_NUMBER_PARAMETER + DOCUMENT)
    public ResponseEntity<Project> getRequirement(
            @PathVariable(PROJECT_NUMBER) Long projectId)
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

        return ResponseEntity.status(CREATED)
                .body(project);
    }

    @GetMapping(SLASH + PROJECT + SLASH + PROJECT_NUMBER_PARAMETER + SLASH +
            DOCUMENT)
    public HttpEntity<byte[]> downloadProjectDocument(
            @PathVariable(PROJECT_NUMBER) Long projectId) {
        Project project = projectService.findById(projectId);
        if (null != project.getFileNameInternal()) {
            throw new InternalException("Can't download document, no filename" +
                    "for project " + projectId);
        }
        var file = Paths.get(project.getFileNameInternal());

        try {
            return getDocument(file, project.getFileName());
        } catch (IOException e) {
            String errorMessage = "Problem getting file for project (" +
                    projectId + ")";
            logger.error(errorMessage);
            throw new InternalException(errorMessage);
        }
    }

    @GetMapping(SLASH + TEMPLATE + SLASH + TEMPLATE_ID_PARAMETER + SLASH +
            DOCUMENT)
    public HttpEntity<byte[]> downloadDocument(
            @PathVariable(TEMPLATE_ID) Long templateId)
            throws IOException {
        Template template = templateService.findById(templateId);
        if (null != template.getFileNameInternal()) {
            throw new InternalException("Can't download document, no filename" +
                    "for template " + templateId);
        }
        var file = Paths.get(template.getFileNameInternal());
        return getDocument(file, template.getTemplateName());
    }

    private HttpEntity<byte[]> getDocument(Path file, String filename)
            throws IOException {

        Resource resource = new UrlResource(file.toUri());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        String header = "Content-Disposition";
        String value = "\"attachment; filename=\"" + filename + "\"";

        responseHeaders.add(header, value);

        byte[] documentBody = new byte[(int) Files.size(file)];
        IOUtils.readFully(resource.getInputStream(),
                documentBody);

        return new HttpEntity<>(documentBody, responseHeaders);
    }
}
