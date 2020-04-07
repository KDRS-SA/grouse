package no.kdrs.grouse.service.interfaces;

import no.kdrs.grouse.document.AsciiDoc;
import no.kdrs.grouse.model.Project;

import java.io.IOException;

public interface IDocumentService {
    void createDocument(Project project) throws IOException;

    void processRequirements(AsciiDoc asciiDoc, Project project);
}
