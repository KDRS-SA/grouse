package no.kdrs.grouse.service.interfaces;

import no.kdrs.grouse.model.Project;

import java.io.IOException;

public interface IDocumentService {
    void createAsciiDocument(Project project, String extension)
            throws IOException;
}
