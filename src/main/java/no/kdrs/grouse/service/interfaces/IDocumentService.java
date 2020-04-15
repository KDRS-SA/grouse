package no.kdrs.grouse.service.interfaces;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface IDocumentService {
    Path createAsciiDocument(UUID projectId, String extension)
            throws IOException;
}
