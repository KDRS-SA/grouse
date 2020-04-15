
package no.kdrs.grouse.controller;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.SupportedFormats;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.links.LinksProject;
import no.kdrs.grouse.model.links.LinksSupportedFormats;
import no.kdrs.grouse.service.interfaces.IDocumentService;
import no.kdrs.grouse.service.interfaces.IProjectService;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import no.kdrs.grouse.utils.CommonController;
import no.kdrs.grouse.utils.exception.InternalException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * DocumentController that has two tasks.
 * <p>
 * 1. Allow for the creation of a requirements document
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
    private CommonController commonController;

    @Value("#{${supported.formats}}")
    private Map<String, String> supportedFormats;

    public DocumentController(IDocumentService documentService,
                              IProjectService projectService,
                              ITemplateService templateService,
                              CommonController commonController) {
        this.documentService = documentService;
        this.projectService = projectService;
        this.templateService = templateService;
        this.commonController = commonController;
    }

    @PostMapping(value = SLASH + PROJECT + SLASH + PROJECT_NUMBER_PARAMETER +
            SLASH + DOCUMENT)
    public ResponseEntity<LinksProject> createRequirementsDocument(
            @PathVariable(PROJECT_NUMBER) UUID projectId)
            throws Exception {
        Project project = projectService.updateProjectFinalised(projectId);
        // We should revisit this in case there is a need to undo setting the
        // document to finalised if the document cannot be created.
        documentService.createAsciiDocument(projectId, "docx");
        return commonController.addProjectLinks(project, CREATED);
    }

    @GetMapping(SLASH + DOCUMENT + SLASH + SUPPORTED_FORMATS)
    public ResponseEntity<LinksSupportedFormats> getSupportedFileFormats() {
        return commonController.addSupportedFormatsLinks(
                new SupportedFormats(supportedFormats), OK);
    }

    @GetMapping(SLASH + PROJECT + SLASH + PROJECT_NUMBER_PARAMETER + SLASH +
            DOCUMENT)
    public HttpEntity<byte[]> downloadProjectDocument(
            HttpServletRequest request,
            @PathVariable(PROJECT_NUMBER) UUID projectId) {

        String accept = request.getHeader(ACCEPT);
        String errorMessage = "Problem getting file for project (" +
                projectId + ")";

        if (null == accept) {
            accept = "application/vnd.oasis.opendocument.text";
        }
        if (accept.equalsIgnoreCase("*/*")) {
            accept = "application/vnd.oasis.opendocument.text";
        }

        String extension = supportedFormats.get(accept);
        if (null != extension) {
            try {
                return getDocument(
                        documentService.createAsciiDocument(projectId,
                                extension), accept);
            } catch (IOException e) {
                errorMessage += ". " + e.getMessage();
                logger.error(errorMessage);
                throw new InternalException(errorMessage);
            }
        }
        errorMessage += ". " + accept + " is not an acceptable file format";
        logger.error(errorMessage);
        throw new InternalException(errorMessage);
    }

    @GetMapping(SLASH + TEMPLATE + SLASH + TEMPLATE_ID_PARAMETER + SLASH +
            DOCUMENT)
    public HttpEntity<byte[]> downloadDocumentTemplate(
            @PathVariable(TEMPLATE_ID) UUID templateId)
            throws IOException {
        Template template = templateService.findById(templateId);
        if (null == template.getFileNameInternal()) {
            throw new InternalException("Can't download document, no filename" +
                    "for template " + templateId);
        }
        var file = Paths.get(template.getFileNameInternal());
        return getDocument(file, template.getTemplateName());
    }

    private HttpEntity<byte[]> getDocument(Path file, String contentType)
            throws IOException {
        Resource resource = new UrlResource(file.toUri());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", contentType);
        String header = "Content-Disposition";
        String value = "\"attachment; filename=\"" + file.getFileName() + "\"";

        responseHeaders.add(header, value);

        byte[] documentBody = new byte[(int) Files.size(file)];
        IOUtils.readFully(resource.getInputStream(),
                documentBody);

        return new HttpEntity<>(documentBody, responseHeaders);
    }
}
