package no.kdrs.grouse.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

import static no.kdrs.grouse.utils.Constants.*;

/**
 * Created by tsodring on 29/03/18.
 *
 * A copy of the fields in Requirements, with a project number. When creating a
 * project, we need to be able to copy all the fields from requirements to
 * project_requirements and associate the copy of the requirements with a
 * project number. This is because the user can change the text, add
 * additional requirements etc.
 *
 * The logic is simpler to implement if we do it this way rather than
 * tracking the requirements that have changed. That's why this approach was
 * chosen. Over time the requirements table might change and dealing or
 * not dealing with such changes might not be the expected functionality. So
 * copying the values from requirements to project_requirements reflects how
 * things were when the project was created.
 *
 */

@Entity
@Table(name = PROJECT_REQUIREMENT_TABLE_NAME)
public class ProjectRequirement
    extends RepresentationModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = REQUIREMENT_PK_ID, nullable = false)
    private Long requirementId;

    /**
     * order (no:kravtype)
     * Used to identify an order that the requirements have to follow
     * Order == 0, is a 'formål'
     */
    @Column(name = SHOW_ORDER)
    private Integer showOrder;

    /**
     * requirementText (no:tekst)*
     */
    @Column(name = REQUIREMENT_TEXT, length = 4000)
    private String requirementText;

     /**
     * priority (no:prioritet)
     *
      * An assigned values as to how important this is in the requirements
      * specifcation
     * e.g.
     *   O - Obligatorisk
     *   1 - Svært viktig for oppdragsgiver
     *   2 - Viktig for oppdragsgiver
     */
     @Column(name = PRIORITY)
     private String priority;

    /**
     * requirement (no:kravnr)
     * An actual requirement number from the standard
     * e.g 5.2.1
     */
    @Column(name = REQUIREMENT_NUMBER)
    private String requirementNumber;

    @XmlElement
    @Column(name = IS_REQUIREMENT)
    private Boolean requirement;

    @XmlElement
    @Column(name = REQUIREMENT_TYPE)
    private String requirementType;

    @Version
    @Column(name = VERSION)
    private Long version;

    @NotNull
    @Column(name = "ownedBy", nullable = false)
    private String ownedBy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "functionality",
            referencedColumnName = "id")
    private ProjectFunctionality referenceFunctionality;

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getRequirementText() {
        return requirementText;
    }

    public void setRequirementText(String requirementText) {
        this.requirementText = requirementText;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequirementNumber() {
        return requirementNumber;
    }

    public void setRequirementNumber(String requirementNumber) {
        this.requirementNumber = requirementNumber;
    }

    public Boolean getRequirement() {
        return requirement;
    }

    public void setRequirement(Boolean requirement) {
        this.requirement = requirement;
    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requirementType) {
        this.requirementType = requirementType;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public ProjectFunctionality getReferenceFunctionality() {
        return referenceFunctionality;
    }

    public void setReferenceFunctionality(ProjectFunctionality referenceFunctionality) {
        this.referenceFunctionality = referenceFunctionality;
    }

    @Override
    public String toString() {
        return "ProjectRequirement{" +
                "requirementId=" + requirementId +
                ", showOrder=" + showOrder +
                ", requirementText='" + requirementText + '\'' +
                ", priority='" + priority + '\'' +
                ", requirementNumber='" + requirementNumber + '\'' +
                ", requirement=" + requirement +
                ", requirementType='" + requirementType + '\'' +
                ", version=" + version +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
