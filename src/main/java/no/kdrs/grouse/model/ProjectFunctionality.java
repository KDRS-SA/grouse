package no.kdrs.grouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static no.kdrs.grouse.utils.Constants.FUNCTIONALITY_NUMBER;
import static no.kdrs.grouse.utils.Constants.VERSION;


/**
 * Created by tsodring on 31/03/18.
 * <p>
 * A copy of the fields in ProjectFunctionality, with a project number. When creating a
 * project, we need to be able to copy all the fields from ProjectFunctionality to
 * project_functionalitys and associate the copy of the functionalitys with a
 * project number. This is because the user needs to have status associated
 * with progress of going through the functionality.
 */

@Entity
@Table(name = "project_functionality_areas",
        uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {FUNCTIONALITY_NUMBER, "project_number"})
        }
)
@XmlRootElement
public class ProjectFunctionality
        extends RepresentationModel
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
    @Column(name = FUNCTIONALITY_NUMBER)
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

    @Version
    @Column(name = VERSION)
    private Long version;

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

    // Link to parent ProjectFunctionality
    @JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private ProjectFunctionality referenceParentFunctionality;

    @OneToMany(mappedBy = "referenceParentFunctionality")
    @OrderBy("projectFunctionalityId ASC")
    private List<ProjectFunctionality> referenceChildProjectFunctionality =
            new ArrayList<>();

    @OneToMany(mappedBy = "referenceFunctionality")
    @OrderBy("show_order ASC")
    private List<ProjectRequirement> referenceProjectRequirement =
            new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_number",
            referencedColumnName = "project_id")
    private Project referenceProject;

    private ProjectFunctionality(FunctionalityBuilder functionalityBuilder) {
        this.functionalityNumber = functionalityBuilder.functionalityNumber;
        this.title = functionalityBuilder.sectionTitle;
        this.description = functionalityBuilder.description;
        this.explanation = functionalityBuilder.explanation;
        this.consequence = functionalityBuilder.consequence;
        this.type = functionalityBuilder.type;
        this.showMe = functionalityBuilder.showMe;
    }

    public ProjectFunctionality() {
    }

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public static class FunctionalityBuilder {

        private String id;
        private String sectionTitle;
        private Integer sectionOrder;
        private String functionalityNumber;
        private String description;
        private String explanation;
        private String consequence;
        private String type;
        private Boolean showMe;

        public ProjectFunctionality.FunctionalityBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ProjectFunctionality.FunctionalityBuilder sectionTitle(String sectionTitle) {
            this.sectionTitle = sectionTitle;
            return this;
        }

        public ProjectFunctionality.FunctionalityBuilder sectionOrder(Integer sectionOrder) {
            this.sectionOrder = sectionOrder;
            return this;
        }

        public ProjectFunctionality.FunctionalityBuilder functionalityNumber(String functionalityNumber) {
            this.functionalityNumber = functionalityNumber;
            return this;
        }

        public ProjectFunctionality.FunctionalityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProjectFunctionality.FunctionalityBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public ProjectFunctionality.FunctionalityBuilder consequence(String consequence) {
            this.consequence = consequence;
            return this;
        }

        public ProjectFunctionality.FunctionalityBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ProjectFunctionality.FunctionalityBuilder showMe(Boolean showMe) {
            this.showMe = showMe;
            return this;
        }

        public ProjectFunctionality build() {
            return new ProjectFunctionality(this);
        }
    }
}
