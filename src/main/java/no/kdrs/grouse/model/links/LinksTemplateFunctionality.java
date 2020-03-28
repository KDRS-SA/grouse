package no.kdrs.grouse.model.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import no.kdrs.grouse.model.TemplateFunctionality;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "templateFunctionalities",
        itemRelation = "templateFunctionality")
@JsonInclude(NON_NULL)
public class LinksTemplateFunctionality
        extends RepresentationModel<LinksTemplateFunctionality> {

    private Long templateFunctionalityId;
    private String functionalityNumber;
    private String title;
    private String description;
    private String consequence;
    private String explanation;
    private Boolean showMe;
    private String type;
    private String ownedBy;
    private Long version;
    private Boolean hasRequirements;
    private Boolean hasFunctionality;

    public LinksTemplateFunctionality(TemplateFunctionality templateFunctionality) {
        this.templateFunctionalityId = templateFunctionality
                .getFunctionalityId();
        this.functionalityNumber = templateFunctionality
                .getFunctionalityNumber();
        this.title = templateFunctionality.getTitle();
        this.description = templateFunctionality.getDescription();
        this.consequence = templateFunctionality.getConsequence();
        this.explanation = templateFunctionality.getExplanation();
        this.showMe = templateFunctionality.getShowMe();
        this.type = templateFunctionality.getType();
        this.ownedBy = templateFunctionality.getOwnedBy();
        this.version = templateFunctionality.getVersion();
        this.hasRequirements = false;
        if (templateFunctionality
                .getReferenceTemplateRequirement()
                .size() > 0) {
            this.hasRequirements = true;
        }
        this.hasFunctionality = false;
        if (templateFunctionality
                .getReferenceChildTemplateFunctionality()
                .size() > 0) {
            this.hasFunctionality = true;
        }
    }

    public Long getTemplateFunctionalityId() {
        return templateFunctionalityId;
    }

    public void setTemplateFunctionalityId(Long templateFunctionalityId) {
        this.templateFunctionalityId = templateFunctionalityId;
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
