package no.kdrs.grouse.model.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import no.kdrs.grouse.model.Project;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "projects", itemRelation = "project")
@JsonInclude(NON_NULL)
public class LinksProject
        extends RepresentationModel<LinksProject> {

    private UUID projectId;
    private String projectName;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private String ownedBy;
    private Integer percentForDocument;
    private Boolean projectComplete;
    private Boolean documentCreated;

    public LinksProject(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.createdDate = project.getCreatedDate();
        this.lastModifiedDate = project.getLastModifidDate();
        this.ownedBy = project.getOwnedBy();
        this.percentForDocument = project.getPercentForDocument();
        this.projectComplete = project.getProjectComplete();
        this.documentCreated = project.getDocumentCreated();
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public Integer getPercentForDocument() {
        return percentForDocument;
    }

    public void setPercentForDocument(Integer percentForDocument) {
        this.percentForDocument = percentForDocument;
    }

    public Boolean getProjectComplete() {
        return projectComplete;
    }

    public void setProjectComplete(Boolean projectComplete) {
        this.projectComplete = projectComplete;
    }

    public Boolean getDocumentCreated() {
        return documentCreated;
    }

    public void setDocumentCreated(Boolean documentCreated) {
        this.documentCreated = documentCreated;
    }
}
