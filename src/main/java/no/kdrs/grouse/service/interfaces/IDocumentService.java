package no.kdrs.grouse.service.interfaces;

import no.kdrs.grouse.document.Document;
import no.kdrs.grouse.model.Project;

import java.io.IOException;

public interface IDocumentService {
    void createDocument(Project project) throws IOException;

    void processRequirements(Document document, Project project);
}
