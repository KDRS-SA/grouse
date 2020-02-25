package no.kdrs.grouse.model.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import no.kdrs.grouse.model.Template;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "templates", itemRelation = "template")
@JsonInclude(NON_NULL)
public class LinksTemplate
        extends RepresentationModel<LinksTemplate> {

    private Long templateId;
    private String templateName;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private String ownedBy;

    public LinksTemplate(Template template) {
        this.templateId = template.getTemplateId();
        this.templateName = template.getTemplateName();
        this.createdDate = template.getCreatedDate();
        this.lastModifiedDate = template.getLastModifiedDate();
        this.ownedBy = template.getOwnedBy();
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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
}
