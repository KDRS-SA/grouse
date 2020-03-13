package no.kdrs.grouse.model.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import no.kdrs.grouse.model.TemplateRequirement;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "templateRequirements",
        itemRelation = "templateRequirement")
@JsonInclude(NON_NULL)
public class LinksTemplateRequirement
        extends RepresentationModel<LinksTemplateRequirement> {

    private Long requirementId;
    private String requirementText;
    private String requirementNumber;
    private String requirementType;
    private Integer showOrder;
    private String priority;
    private Boolean requirement;

    public LinksTemplateRequirement(TemplateRequirement templateRequirement) {
        this.requirementId = templateRequirement.getRequirementId();
        this.requirementText = templateRequirement.getRequirementText();
        this.requirementNumber = templateRequirement.getRequirementNumber();
        this.requirementType = templateRequirement.getRequirementType();
        this.showOrder = templateRequirement.getShowOrder();
        this.priority = templateRequirement.getPriority();
        this.requirement = templateRequirement.getRequirement();
    }

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirementText() {
        return requirementText;
    }

    public void setRequirementText(String requirementText) {
        this.requirementText = requirementText;
    }

    public String getRequirementNumber() {
        return requirementNumber;
    }

    public void setRequirementNumber(String requirementNumber) {
        this.requirementNumber = requirementNumber;
    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requirementType) {
        this.requirementType = requirementType;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Boolean getRequirement() {
        return requirement;
    }

    public void setRequirement(Boolean requirement) {
        this.requirement = requirement;
    }
}
