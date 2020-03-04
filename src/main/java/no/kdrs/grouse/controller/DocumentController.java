
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.service.interfaces.IDocumentService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import no.kdrs.grouse.utils.exception.InternalException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static no.kdrs.grouse.utils.Constants.*;

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

    /*
    I'm not sure what the point of this endpoint / code is. It seems to allow
    the client to POST a project number from the root of the application
    followed by /document and it creates the document. We should probably
    revisit this.

    @PostMapping(value = SLASH + PROJECT_NUMBER_PARAMETER + DOCUMENT)
    public ResponseEntity<Project> getRequirement(
            @PathVariable(PROJECT_NUMBER) Long projectId)
            throws Exception {

        Project project = projectService.findById(projectId);
        documentService.createDocument(project);
        project.setDocumentCreated(true);
        project.setProjectComplete(true);
        projectService.update(projectId, project);

        project.add(linkTo(methodOn(ProjectController.class).
                getProject(projectId)).withSelfRel());

        project.add(linkTo(methodOn(ProjectController.class).
                getFunctionalityForProject(projectId))
                .withRel(FUNCTIONALITY));

        project.add(linkTo(DocumentController.class, DocumentController.class.
                getMethod("downloadDocumentProject", Long.class), projectId).
                withRel(DOCUMENT));

        return ResponseEntity.status(CREATED)
                .body(project);
    }
*/

    @GetMapping(SLASH + PROJECT + SLASH + PROJECT_NUMBER_PARAMETER + SLASH +
            DOCUMENT)
    public HttpEntity<byte[]> downloadDocumentProject(
            @PathVariable(PROJECT_NUMBER) Long projectId)
            throws IOException {
        Project project = projectService.findById(projectId);
        if (null != project.getFileNameInternal()) {
            throw new InternalException("Can't download document, no filename" +
                    "for project " + projectId);
        }
        var file = Paths.get(project.getFileNameInternal());
        return getDocument(file, project.getFileName());
    }

    @GetMapping(SLASH + TEMPLATE + SLASH + TEMPLATE_ID_PARAMETER + SLASH +
            DOCUMENT)
    public HttpEntity<byte[]> downloadDocumentTemplate(
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
