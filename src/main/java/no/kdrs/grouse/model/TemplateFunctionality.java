package no.kdrs.grouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;
import static no.kdrs.grouse.utils.Constants.*;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Entity
@Table(name = TEMPLATE_FUNCTIONALITY_AREAS_TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class TemplateFunctionality
        extends RepresentationModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Number of the functional area. e.g 1.2.3
     * This is an internal number that the project themselves decides
     */
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = FUNCTIONALITY_PK_ID, nullable = false, updatable = false)
    private Long functionalityId;

    @Column(name = FUNCTIONALITY_NUMBER, nullable = false, updatable = false)
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

    @Column(name = "section_order")
    private Integer sectionOrder;

    /**
     * Whether or not to be displayed in menu
     */
    @Column(name = "show_me")
    private Boolean showMe;

    /**
     * Type of requirement e.g. 'funksjonell', 'teknisk', 'integrasjon'
     */
    @Column(name = "type")
    private String type;

    @CreatedBy
    @NotNull
    @Column(name = OWNED_BY, nullable = false)
    private String ownedBy;

    /**
     * The date the template functionality was created
     */
    @CreatedDate
    @Column(name = CREATED_DATE)
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime createdDate;

    /**
     * The date the template was changed
     */
    @LastModifiedDate
    @Column(name = CHANGED_DATE)
    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime lastModifiedDate;

    @Version
    @Column(name = VERSION)
    private Long version;

    @ManyToOne
    @JoinColumn(name = "id")
    private Template referenceTemplate;

    // Link to parent TemplateFunctionality
    @ManyToOne
    @JoinColumn(name = "parent")
    private TemplateFunctionality referenceParentFunctionality;

    @OneToMany(mappedBy = "referenceParentFunctionality")
    private List<TemplateFunctionality> referenceChildTemplateFunctionality =
            new ArrayList<>();

    @OneToMany(mappedBy = "referenceFunctionality")
    @OrderBy("functionalityId ASC")
    private List<TemplateRequirement> referenceTemplateRequirement =
            new ArrayList<>();

    public TemplateFunctionality() {
    }

    private TemplateFunctionality(FunctionalityBuilder functionalityBuilder) {
        this.functionalityNumber = functionalityBuilder.functionalityNumber;
        this.title = functionalityBuilder.sectionTitle;
        this.description = functionalityBuilder.description;
        this.explanation = functionalityBuilder.explanation;
        this.consequence = functionalityBuilder.consequence;
        this.type = functionalityBuilder.type;
        this.showMe = functionalityBuilder.showMe;
        this.sectionOrder = functionalityBuilder.sectionOrder;
    }

    public Long getFunctionalityId() {
        return functionalityId;
    }

    public void setFunctionalityId(Long functionalityId) {
        this.functionalityId = functionalityId;
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

    public Integer getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(Integer sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Template getReferenceTemplate() {
        return referenceTemplate;
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

    public void setReferenceTemplate(Template referenceTemplate) {
        this.referenceTemplate = referenceTemplate;
    }

    public List<TemplateFunctionality> getReferenceChildTemplateFunctionality() {
        return referenceChildTemplateFunctionality;
    }

    public void setReferenceChildTemplateFunctionality(
            List<TemplateFunctionality> referenceChildTemplateFunctionality) {
        this.referenceChildTemplateFunctionality =
                referenceChildTemplateFunctionality;
    }

    public void addReferenceChildTemplateFunctionality(
            TemplateFunctionality childTemplateFunctionality) {
        this.referenceChildTemplateFunctionality.add(
                childTemplateFunctionality);
    }

    @JsonIgnore
    public TemplateFunctionality getReferenceParentFunctionality() {
        return referenceParentFunctionality;
    }

    @XmlTransient
    public void setReferenceParentFunctionality(
            TemplateFunctionality referenceParentFunctionality) {
        this.referenceParentFunctionality = referenceParentFunctionality;
    }

    public List<TemplateRequirement> getReferenceTemplateRequirement() {
        return referenceTemplateRequirement;
    }

    public void setReferenceTemplateRequirement(
            List<TemplateRequirement> referenceTemplateRequirement) {
        this.referenceTemplateRequirement =
                referenceTemplateRequirement;
    }

    public void setTemplateRequirement(
            TemplateRequirement templateRequirement) {
        this.referenceTemplateRequirement.add(templateRequirement);
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

        public FunctionalityBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FunctionalityBuilder sectionTitle(String sectionTitle) {
            this.sectionTitle = sectionTitle;
            return this;
        }

        public FunctionalityBuilder sectionOrder(Integer sectionOrder) {
            this.sectionOrder = sectionOrder;
            return this;
        }

        public FunctionalityBuilder functionalityNumber(String functionalityNumber) {
            this.functionalityNumber = functionalityNumber;
            return this;
        }

        public FunctionalityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FunctionalityBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public FunctionalityBuilder consequence(String consequence) {
            this.consequence = consequence;
            return this;
        }

        public FunctionalityBuilder type(String type) {
            this.type = type;
            return this;
        }

        public FunctionalityBuilder showMe(Boolean showMe) {
            this.showMe = showMe;
            return this;
        }

        public TemplateFunctionality build() {
            return new TemplateFunctionality(this);
        }
    }
}
