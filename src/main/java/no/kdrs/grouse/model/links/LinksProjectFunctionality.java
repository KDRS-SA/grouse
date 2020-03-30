package no.kdrs.grouse.model.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import no.kdrs.grouse.model.ProjectFunctionality;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "projectFunctionalities",
        itemRelation = "projectFunctionality")
@JsonInclude(NON_NULL)
public class LinksProjectFunctionality
        extends RepresentationModel<LinksProjectFunctionality> {

    private Long projectFunctionalityId;
    private String functionalityNumber;
    private String title;
    private String description;
    private String consequence;
    private String explanation;
    private Boolean showMe;
    private Boolean processed;
    private Boolean active;
    private String type;
    private String ownedBy;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private Long version;
    private Boolean hasRequirements;
    private Boolean hasFunctionality;

    public LinksProjectFunctionality(ProjectFunctionality projectFunctionality) {
        this.projectFunctionalityId = projectFunctionality
                .getProjectFunctionalityId();
        this.functionalityNumber = projectFunctionality
                .getFunctionalityNumber();
        this.title = projectFunctionality.getTitle();
        this.description = projectFunctionality.getDescription();
        this.consequence = projectFunctionality.getConsequence();
        this.explanation = projectFunctionality.getExplanation();
        this.showMe = projectFunctionality.getShowMe();
        this.processed = projectFunctionality.getProcessed();
        this.active = projectFunctionality.getActive();
        this.type = projectFunctionality.getType();
        this.ownedBy = projectFunctionality.getOwnedBy();
        this.createdDate = projectFunctionality.getCreatedDate();
        this.lastModifiedDate = projectFunctionality.getLastModifiedDate();
        this.version = projectFunctionality.getVersion();
        this.hasRequirements = false;
        if (projectFunctionality
                .getReferenceProjectRequirement()
                .size() > 0) {
            this.hasRequirements = true;
        }
        this.hasFunctionality = false;
        if (projectFunctionality
                .getReferenceChildProjectFunctionality()
                .size() > 0) {
            this.hasFunctionality = true;
        }
    }

    public Long getProjectFunctionalityId() {
        return projectFunctionalityId;
    }

    public void setProjectFunctionalityId(Long projectFunctionalityId) {
        this.projectFunctionalityId = projectFunctionalityId;
    }

    public String getFunctionalityNumber() {
        return functionalityNumber;
    }

    public void setFunctionalityNumber(String functionalityNumber) {
        this.functionalityNumber = functionalityNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean hasRequirements() {
        return hasRequirements;
    }

    public void setHasRequirements(Boolean hasRequirements) {
        this.hasRequirements = hasRequirements;
    }

    public Boolean hasFunctionality() {
        return hasFunctionality;
    }

    public void setHasFunctionality(Boolean hasFunctionality) {
        this.hasFunctionality = hasFunctionality;
    }
}
