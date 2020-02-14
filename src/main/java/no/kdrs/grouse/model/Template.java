package no.kdrs.grouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.kdrs.grouse.utils.exception.ConcurrencyException;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;
import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Entity
public class Template
        extends RepresentationModel {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = TEMPLATE_PK_ID, nullable = false, updatable = false)
    private Long templateId;

    @Column(name = TEMPLATE_NAME)
    private String templateName;

    /**
     * The date the project was created
     */
    @CreatedDate
    @Column(name = CREATED_DATE)
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime createdDate;

    /**
     * The date the project was accessed
     */
    @LastModifiedDate
    @Column(name = CHANGED_DATE)
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime lastModifiedDate;

    @CreatedBy
    @NotNull
    @Column(name = OWNED_BY, nullable = false)
    private String ownedBy;

    @Version
    @Column(name = VERSION)
    private Long version;

    @OneToMany(mappedBy = "referenceTemplate")
    private List<TemplateFunctionality> referenceTemplateFunctionality =
            new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "referenceTemplate")
    private List<TemplateRequirement> referenceTemplateRequirement;

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

    public List<TemplateFunctionality> getReferenceTemplateFunctionality() {
        return referenceTemplateFunctionality;
    }

    public void setReferenceTemplateFunctionality(List<TemplateFunctionality> referenceTemplateFunctionality) {
        this.referenceTemplateFunctionality = referenceTemplateFunctionality;
    }
}
