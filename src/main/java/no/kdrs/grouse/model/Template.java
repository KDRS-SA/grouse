package no.kdrs.grouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import no.kdrs.grouse.utils.exception.ConcurrencyException;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Template
        extends RepresentationModel {

    @Id
    @Column(name = TEMPLATE_PK_ID, nullable = false, updatable = false)
    @Type(type = "uuid-char")
    private UUID templateId;

    @Column(name = TEMPLATE_NAME)
    private String templateName;

    @Column(name = TEMPLATE_DESCRIPTION, length = 10000)
    private String description;

    @Column(name = TEMPLATE_AREA)
    private String area;

    @Column(name = TEMPLATE_TYPE)
    private String type;

    /**
     * The date the project was created
     */
    @CreatedDate
    @Column(name = CREATED_DATE, updatable = false)
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime createdDate;

    /**
     * Name of the requirements document stored on disk
     */
    @Column(name = TEMPLATE_FILE_NAME_INTERNAL)
    private String fileNameInternal;

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

    @JsonIgnore
    @OneToMany(mappedBy = "referenceTemplate")
    private List<TemplateFunctionality> referenceTemplateFunctionality =
            new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "referenceTemplate")
    private List<TemplateRequirement> referenceTemplateRequirement;

    public UUID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileNameInternal() {
        return fileNameInternal;
    }

    public void setFileNameInternal(String fileNameInternal) {
        this.fileNameInternal = fileNameInternal;
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

    public void setReferenceTemplateFunctionality(
            List<TemplateFunctionality> referenceTemplateFunctionality) {
        this.referenceTemplateFunctionality = referenceTemplateFunctionality;
    }

    public void addTemplateFunctionality(
            TemplateFunctionality templateFunctionality) {
        this.referenceTemplateFunctionality.add(templateFunctionality);
    }
}
