package no.kdrs.grouse.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@Table(name = "project_requirements")
public class ProjectRequirement
    extends RepresentationModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "requirement_id", nullable = false)
    private Long projectRequirementId;

    /**
     * order (no:kravtype)
     * Used to identify an order that the requirements have to follow
     * Order == 0, is a 'formål'
     */
    @Column(name = "show_order")
    private Integer order;

    /**
     * requirementText (no:tekst)*
     */
    @Column(name = "requirement_text", length = 4000)
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
    @Column(name = "priority")
    private String priority;

    /**
     * requirement (no:kravnr)
     * An actual requirement number from the standard
     * e.g 5.2.1
     */
    @Column(name = "requirement_number")
    private String requirementNumber;

    @NotNull
    @Column(name = "ownedBy", nullable = false)
    private String ownedBy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "functionality",
            referencedColumnName = "id")
    private ProjectFunctionality referenceFunctionality;

    public Long getProjectRequirementId() {
        return projectRequirementId;
    }

    public void setProjectRequirementId(Long projectRequirementId) {
        this.projectRequirementId = projectRequirementId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
                "projectRequirementId=" + projectRequirementId +
                ", order=" + order +
                ", requirementText='" + requirementText + '\'' +
                ", priority='" + priority + '\'' +
                ", requirementNumber='" + requirementNumber + '\'' +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
