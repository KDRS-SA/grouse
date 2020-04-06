package no.kdrs.grouse.service;

import no.kdrs.grouse.document.Document;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.service.interfaces.IDocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.GROUSE;

/**
 * Created by tsodring on 10/28/17.
 */
@Component
@Transactional
public class DocumentService
        implements IDocumentService {

    @Value("${storage.location}")
    private String storageLocation;

    @Override
    public void createDocument(Project project) throws IOException {

        String filename = storageLocation + File.separator +
                GROUSE + "-" + project.getProjectId().toString() + ".docx";
        project.setFileNameInternal(filename);
        FileOutputStream file = new FileOutputStream(filename);
        Document document = new Document(file);
        processRequirements(document, project);
        document.close();
        file.flush();
        file.close();
        // TODO: Temp disabled so we can call multiple times
        //project.setDocumentCreated(true);
    }

    /**
     * Populate the requirements document with details from the database
     *
     * @param document The Word document
     * @param project  An instance of the relevant Project object
     */
    public void processRequirements(Document document, Project project) {

        List<ProjectFunctionality> projectFunctionalities =
                project.getReferenceProjectFunctionality();
        processFunctionalities(document, project, projectFunctionalities);
    }

    protected void processFunctionalities(
            Document document, Project project,
            List<ProjectFunctionality> projectFunctionalities) {

        for (ProjectFunctionality projectFunctionality :
                projectFunctionalities) {

            String title = projectFunctionality.getTitle();

            if (null != title && title.length() == 1) {
                document.addHeading1(title);
            } else if (null != title && title.length() > 1) {
                document.addHeading2(title);
            }

            String description = projectFunctionality.getDescription();
            if (!projectFunctionality.getShowMe() && description != null) {
                if (project.getOrganisationName() != null) {
                    description = description.replace("ORG_NAVN",
                            project.getOrganisationName());
                    document.addText(description);
                }
            }
            if (projectFunctionality
                    .getReferenceProjectRequirement().size() > 0) {
                processRequirements(document, projectFunctionality
                                .getReferenceProjectRequirement(),
                        projectFunctionality);
            }
            if (projectFunctionality
                    .getReferenceChildProjectFunctionality().size() > 0) {
                processFunctionalities(document, project, projectFunctionality
                        .getReferenceChildProjectFunctionality());
            }
        }
    }

    protected void processRequirements(
            Document document,
            List<ProjectRequirement> projectRequirements,
            ProjectFunctionality projectFunctionality) {
        document.createTable(projectRequirements.size(), projectFunctionality);
        for (ProjectRequirement projectRequirement : projectRequirements) {
            document.addRow(projectRequirement);
        }
    }
}
