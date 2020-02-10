package no.kdrs.grouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by tsodring on 9/8/17.
 */

@Entity
@Table(name = "projects")
@XmlRootElement
public class Project
        extends RepresentationModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "project_id", nullable = false, updatable = false)
    private Long projectId;

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
    private Boolean documentCreated;

    /**
     * Used to identify if all the steps have been
     * carried out in the project
     */
    @Column(name = "project_complete")
    private Boolean projectComplete = false;

    /**
     * The date the project was created
     */
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    /**
     * The date the project was accessed
     */
    @Column(name = "changed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changedDate;

    @NotNull
    @Column(name = "ownedBy", nullable = false)
    private String ownedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "referenceProject")
    private List<ProjectRequirement> referenceProjectRequirement;

    @JsonIgnore
    @OneToMany(mappedBy = "referenceProject")
    @OrderBy("projectFunctionalityId ASC")
    private List<ProjectFunctionality> referenceProjectFunctionality;

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(Date changedDate) {
        this.changedDate = changedDate;
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

    public List<ProjectRequirement> getReferenceProjectRequirement() {
        return referenceProjectRequirement;
    }

    public void setReferenceProjectRequirement(List<ProjectRequirement> referenceProjectRequirement) {
        this.referenceProjectRequirement = referenceProjectRequirement;
    }

    public List<ProjectFunctionality> getReferenceProjectFunctionality() {
        return referenceProjectFunctionality;
    }

    public void setReferenceProjectFunctionality(
            List<ProjectFunctionality> referenceProjectFunctionality) {
        this.referenceProjectFunctionality = referenceProjectFunctionality;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", organisationName='" + organisationName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", createdDate=" + createdDate +
                ", changedDate=" + changedDate +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
