package no.kdrs.grouse.service;

import no.kdrs.grouse.document.AsciiDoc;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import no.kdrs.grouse.service.interfaces.IDocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.GROUSE;

@Component
@Transactional
public class DocumentService
        extends GrouseService
        implements IDocumentService {

    @Value("${storage.location}")
    private String storageLocation;

    @Override
    public void createAsciiDocument(Project project) throws IOException {
        checkAccess(project.getProjectId());
        //Asciidoctor asciidoctor = create();
        String filename = storageLocation + File.separator +
                GROUSE + "-" + project.getProjectId().toString() + ".adoc";
        // TODO: Remove filename internal. As filename is based on project,
        //  based on UUID. It is settable automatically
        project.setFileNameInternal(filename);

        File file = new File(filename);
        FileWriter fileWriter = new FileWriter(file);
        AsciiDoc asciiDoc = new AsciiDoc(fileWriter);
        asciiDoc.addSectionHeader(project.getProjectName(), 1);
        asciiDoc.addToc();
        processAllRequirements(asciiDoc, project);
        asciiDoc.close();
    }

    /**
     * Populate the requirements asciiDoc with details from the database
     *
     * @param asciiDoc The asciiDoc
     * @param project  An instance of the relevant Project object
     */
    public void processAllRequirements(AsciiDoc asciiDoc, Project project)
            throws IOException {
        Integer requirementNumber = 1;
        processFunctionalities(asciiDoc, project,
                requirementNumber,
                project.getReferenceProjectFunctionality());
    }

    protected void processFunctionalities(
            AsciiDoc asciiDoc, Project project,
            Integer level, List<ProjectFunctionality> projectFunctionalities)
            throws IOException {

        level++;

        for (ProjectFunctionality projectFunctionality :
                projectFunctionalities) {
            String title = projectFunctionality.getTitle();
            asciiDoc.addSectionHeader(title, level);
            String description = projectFunctionality.getDescription();
            if (!projectFunctionality.getShowMe() && description != null) {
                if (project.getOrganisationName() != null) {
                    description = description.replace("ORG_NAVN",
                            project.getOrganisationName());
                }
                asciiDoc.addText(description);
            }
            if (projectFunctionality
                    .getReferenceProjectRequirement().size() > 0) {
                processRequirements(asciiDoc, projectFunctionality);
            }
            if (projectFunctionality
                    .getReferenceChildProjectFunctionality().size() > 0) {
                processFunctionalities(asciiDoc, project, level,
                        projectFunctionality
                                .getReferenceChildProjectFunctionality());
            }
            asciiDoc.addPageBreak();
        }
    }

    protected void processRequirements(
            AsciiDoc asciiDoc, ProjectFunctionality projectFunctionality)
            throws IOException {
        List<ProjectRequirement> projectRequirements = projectFunctionality
                .getReferenceProjectRequirement();
        asciiDoc.createTable(projectFunctionality.getTitle());
        for (int i = 0; i < projectRequirements.size(); i++) {
            asciiDoc.addRow(projectRequirements.get(i),
                    projectFunctionality.getFunctionalityNumber(), i + 1);
        }
        asciiDoc.endTable();
    }
}
