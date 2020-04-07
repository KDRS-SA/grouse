package no.kdrs.grouse.service;

import no.kdrs.grouse.document.AsciiDoc;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.service.interfaces.IDocumentService;
import org.asciidoctor.Asciidoctor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.GROUSE;
import static org.asciidoctor.Asciidoctor.Factory.create;

/**
 * Created by tsodring on 10/28/17.
 */
@Component
@Transactional
public class DocumentService
    extends GrouseService
        implements IDocumentService {

    @Value("${storage.location}")
    private String storageLocation;

    @Override
    public void createDocument(Project project) throws IOException {
        checkAccess(project.getProjectId());

        Asciidoctor asciidoctor = create();
        String filename = storageLocation + File.separator +
                GROUSE + "-" + project.getProjectId().toString() + ".adoc";

        project.setFileNameInternal(filename);
        FileOutputStream file = new FileOutputStream(filename);
        AsciiDoc asciiDoc = new AsciiDoc(file);
        processRequirements(asciiDoc, project);
        asciiDoc.close();
        file.flush();
        file.close();
        // TODO: Temp disabled so we can call multiple times
        //project.setDocumentCreated(true);
    }

    /**
     * Populate the requirements asciiDoc with details from the database
     *
     * @param asciiDoc The Word asciiDoc
     * @param project  An instance of the relevant Project object
     */
    public void processRequirements(AsciiDoc asciiDoc, Project project) {

        List<ProjectFunctionality> projectFunctionalities =
                project.getReferenceProjectFunctionality();
        processFunctionalities(asciiDoc, project, projectFunctionalities);
    }

    protected void processFunctionalities(
            AsciiDoc asciiDoc, Project project,
            List<ProjectFunctionality> projectFunctionalities) {

        for (ProjectFunctionality projectFunctionality :
                projectFunctionalities) {

            String title = projectFunctionality.getTitle();

            if (null != title && title.length() == 1) {
                asciiDoc.addHeading1(title);
            } else if (null != title && title.length() > 1) {
                asciiDoc.addHeading2(title);
            }

            String description = projectFunctionality.getDescription();
            if (!projectFunctionality.getShowMe() && description != null) {
                if (project.getOrganisationName() != null) {
                    description = description.replace("ORG_NAVN",
                            project.getOrganisationName());
                    asciiDoc.addText(description);
                }
            }
            if (projectFunctionality
                    .getReferenceProjectRequirement().size() > 0) {
                processRequirements(asciiDoc, projectFunctionality
                                .getReferenceProjectRequirement(),
                        projectFunctionality);
            }
            if (projectFunctionality
                    .getReferenceChildProjectFunctionality().size() > 0) {
                processFunctionalities(asciiDoc, project, projectFunctionality
                        .getReferenceChildProjectFunctionality());
            }
        }
    }

    protected void processRequirements(
            AsciiDoc asciiDoc,
            List<ProjectRequirement> projectRequirements,
            ProjectFunctionality projectFunctionality) {
        asciiDoc.createTable(projectRequirements.size(), projectFunctionality);
        for (ProjectRequirement projectRequirement : projectRequirements) {
            asciiDoc.addRow(projectRequirement);
        }
    }
}
