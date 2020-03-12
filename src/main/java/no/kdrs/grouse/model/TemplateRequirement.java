package no.kdrs.grouse.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static no.kdrs.grouse.utils.Constants.*;

/**
 * Created by tsodring on 9/8/17.
 */

@Entity
@Table(name = TEMPLATE_REQUIREMENT_TABLE_NAME)
@XmlRootElement(name = "requirement")
@XmlAccessorType(FIELD)
public class TemplateRequirement
        extends RepresentationModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = REQUIREMENT_PK_ID, nullable = false, updatable = false)
    private Long requirementId;

    /**
     * order (no:kravtype)
     * Used to identify an order that the requirements have to follow
     * Order == 0, is a formål
     */
    @XmlElement
    @Column(name = SHOW_ORDER)
    private Integer showOrder;

    /**
     * requirementText (no:tekst)*
     */
    @XmlElement
    @Column(name = REQUIREMENT_TEXT, length = 4000)
    private String requirementText;

    /**
     * priority (no:prioritet)
     * <p>
     * An assigned values as to how important this is in the requirements
     * specifcation
     * e.g.
     * O - Obligatorisk
     * 1 - Svært viktig for oppdragsgiver
     * 2 - Viktig for oppdragsgiver
     */
    @XmlElement
    @Column(name = PRIORITY)
    private String priority;

    /**
     * requirement (no:kravnr)
     * An actual requirement number from the standard
     * e.g 5.2.1
     */
    @XmlElement
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

    @ManyToOne
    @JoinColumn(name = FUNCTIONALITY_FK_ID,
            referencedColumnName = FUNCTIONALITY_NUMBER)
    private TemplateFunctionality referenceFunctionality;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = TEMPLATE_FK_ID,
            referencedColumnName = TEMPLATE_PK_ID)
    private Template referenceTemplate;

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

    public TemplateFunctionality getFunctionality() {
        return referenceFunctionality;
    }

    public void setFunctionality(TemplateFunctionality functionality) {
        this.referenceFunctionality = functionality;
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

    public TemplateFunctionality getReferenceTemplateFunctionality() {
        return referenceFunctionality;
    }

    public void setReferenceTemplate(Template referenceTemplate) {
        this.referenceTemplate = referenceTemplate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TemplateRequirement{" +
                "requirementId=" + requirementId +
                ", showOrder=" + showOrder +
                ", requirementText='" + requirementText + '\'' +
                ", priority='" + priority + '\'' +
                ", requirementNumber='" + requirementNumber + '\'' +
                ", requirement=" + requirement +
                ", requirementType='" + requirementType + '\'' +
                ", version=" + version +
                '}';
    }
}
