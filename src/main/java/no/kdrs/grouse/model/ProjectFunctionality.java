package no.kdrs.grouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by tsodring on 31/03/18.
 * <p>
 * A copy of the fields in Functionality, with a project number. When creating a
 * project, we need to be able to copy all the fields from Functionality to
 * project_functionalitys and associate the copy of the functionalitys with a
 * project number. This is because the user needs to have status associated
 * with progress of going through the functionality.
 */

@Entity
@Table(name = "project_functionality_areas",
        uniqueConstraints = {
        @UniqueConstraint(
                columnNames={"functionality_number", "project_number"})
        }
)
@XmlRootElement
public class ProjectFunctionality
        extends ResourceSupport
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long projectFunctionalityId;

    /**
     * Number of the functional area. e.g 1.2.3
     * This is an internal number that the project themselves decides
     */
    @Column(name = "functionality_number")
    private String functionalityNumber;

    /**
     * Text of the functionality
     * <p>
     * e.g functionality_number '1.7.2.19' has title
     * 'Krav knyttet til masseoppdatering'
     */
    @Column(name = "title")
    private String title;

    /**
     * Description of functionality
     */
    @Column(name = "description", length = 4000)
    private String description;


    /**
     * Description of consequence of excluding this functionality
     */
    @Column(name = "consequence", length = 4000)
    private String consequence;

    /**
     * Description of consequence of excluding this functionality
     */
    @Column(name = "explanation", length = 4000)
    private String explanation;

    /**
     * Whether or not to be displayed in menu
     */
    @Column(name = "show_me")
    private Boolean showMe;

    /**
     * Workflow status, if this has already been processed or not
     * Also useful for when reloading the project
     */
    @Column(name = "processed")
    private Boolean processed;


    /**
     * Used by the GUI. Should not really be here, but leaving it here
     * while developing
     */
    @Column(name = "active")
    private Boolean active;

    /**
     * Type of requirement e.g. 'funksjonell', 'teknisk', 'integrasjon'
     */
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "ownedBy", nullable = false)
    private String ownedBy;

    // Link to parent Functionality
    @JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private ProjectFunctionality referenceParentFunctionality;

    @OneToMany(mappedBy = "referenceParentFunctionality")
    @OrderBy("projectFunctionalityId ASC")
    private List<ProjectFunctionality> referenceChildProjectFunctionality =
            new ArrayList<>();

    @OneToMany(mappedBy = "referenceFunctionality")
    @OrderBy("order ASC")
    private List<ProjectRequirement> referenceProjectRequirement =
            new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_number",
            referencedColumnName = "project_id")
    private Project referenceProject;

    public Long getProjectFunctionalityId() {
        return projectFunctionalityId;
    }

    public void setProjectFunctionalityId(Long projectFunctionalityId) {
        this.projectFunctionalityId = projectFunctionalityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFunctionalityNumber() {
        return functionalityNumber;
    }

    public void setFunctionalityNumber(String functionalityNumber) {
        this.functionalityNumber = functionalityNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Boolean getShowMe() {
        return showMe;
    }

    public void setShowMe(Boolean showMe) {
        this.showMe = showMe;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public ProjectFunctionality getReferenceParentFunctionality() {
        return referenceParentFunctionality;
    }

    public void setReferenceParentFunctionality(
            ProjectFunctionality referenceParentFunctionality) {
        this.referenceParentFunctionality = referenceParentFunctionality;
    }

    public List<ProjectFunctionality> getReferenceChildProjectFunctionality() {
        return referenceChildProjectFunctionality;
    }

    public void setReferenceChildProjectFunctionality(
            List<ProjectFunctionality> referenceChildProjectFunctionality) {
        this.referenceChildProjectFunctionality =
                referenceChildProjectFunctionality;
    }

    public void addReferenceChildProjectFunctionality(
            ProjectFunctionality projectFunctionality) {
        this.referenceChildProjectFunctionality.add(projectFunctionality);
    }


    public List<ProjectRequirement> getReferenceProjectRequirement() {
        return referenceProjectRequirement;
    }

    public void setReferenceProjectRequirement(
            List<ProjectRequirement> referenceProjectRequirement) {
        this.referenceProjectRequirement = referenceProjectRequirement;
    }

    public void addReferenceProjectRequirement(
            ProjectRequirement projectRequirement) {
        referenceProjectRequirement.add(projectRequirement);
    }

    public Project getReferenceProject() {
        return referenceProject;
    }

    public void setReferenceProject(Project referenceProject) {
        this.referenceProject = referenceProject;
    }

    @Override
    public String toString() {
        return "ProjectFunctionality{" +
                "projectFunctionalityId=" + projectFunctionalityId +
                ", functionalityNumber='" + functionalityNumber + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", consequence='" + consequence + '\'' +
                ", explanation='" + explanation + '\'' +
                ", showMe=" + showMe +
                ", processed=" + processed +
                ", active=" + active +
                ", type='" + type + '\'' +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
