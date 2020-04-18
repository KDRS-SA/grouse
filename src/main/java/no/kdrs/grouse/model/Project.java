package no.kdrs.grouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.kdrs.grouse.utils.exception.ConcurrencyException;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

/**
 * Created by tsodring on 9/8/17.
 */

@Entity
@Table(name = PROJECT_TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
@XmlRootElement
public class Project {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = PROJECT_PK_ID, nullable = false, updatable = false)
    @Type(type = "uuid-char")
    private UUID projectId;

    /**
     * Assignable descriptive name to the project e.g
     * 'Eksempel kommune nytt sak/arkiv system 2017'
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * Name of the organisation
     */
    @Column(name = "organisation_name")
    private String organisationName;

    /**
     * Name of the requirements document when it is downloaded
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * Name of the requirements document stored on disk
     */
    @Column(name = "file_name_internal")
    private String fileNameInternal;

    /**
     * Name of the requirements document
     */
    @Column(name = "document_created")
    private Boolean documentCreated = false;

    /**
     * Used to identify if all the steps have been
     * carried out in the project
     */
    @Column(name = "project_complete")
    private Boolean projectComplete = false;


    /**
     * Value showing how much 0-100 (%) of requirements that have to
     * be accepted before the user can download the document
     */
    @Column(name = PERCENT_FOR_DOCUMENT)
    private Integer percentForDocument;

    /**
     * The date the project was created
     */
    @CreatedDate
    @Column(name = "created_date")
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime createdDate;

    /**
     * The date the project was accessed
     */
    @LastModifiedDate
    @Column(name = "changed_date")
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime lastModifidDate;

    @NotNull
    @Column(name = "ownedBy", nullable = false)
    private String ownedBy;

    @Version
    @Column(name = VERSION)
    private Long version;

    @JsonIgnore
    @OneToMany(mappedBy = "referenceProject")
    @OrderBy("projectFunctionalityId ASC")
    private List<ProjectFunctionality> referenceProjectFunctionality;

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

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getPercentForDocument() {
        return percentForDocument;
    }

    public void setPercentForDocument(Integer percentForDocument) {
        this.percentForDocument = percentForDocument;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getLastModifidDate() {
        return lastModifidDate;
    }

    public void setLastModifidDate(OffsetDateTime lastModifidDate) {
        this.lastModifidDate = lastModifidDate;
    }

    public Boolean getDocumentCreated() {
        return documentCreated;
    }

    public void setDocumentCreated(Boolean documentCreated) {
        this.documentCreated = documentCreated;
    }

    public Boolean getProjectComplete() {
        return projectComplete;
    }

    public void setProjectComplete(Boolean projectComplete) {
        this.projectComplete = projectComplete;
    }

    public String getFileNameInternal() {
        return fileNameInternal;
    }

    public void setFileNameInternal(String fileNameInternal) {
        this.fileNameInternal = fileNameInternal;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        if (!this.version.equals(version)) {
            throw new ConcurrencyException(
                    "Concurrency Exception. Old version [" + this.version +
                            "], new version [" + version + "]");
        }
    }

    public List<ProjectFunctionality> getReferenceProjectFunctionality() {
        return referenceProjectFunctionality;
    }

    public void setReferenceProjectFunctionality(
            List<ProjectFunctionality> referenceProjectFunctionality) {
        this.referenceProjectFunctionality = referenceProjectFunctionality;
    }

    public void addProjectFunctionality(
            ProjectFunctionality projectFunctionality) {
        this.referenceProjectFunctionality.add(projectFunctionality);
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", organisationName='" + organisationName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifidDate=" + lastModifidDate +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}


