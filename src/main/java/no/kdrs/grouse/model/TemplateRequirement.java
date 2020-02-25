package no.kdrs.grouse.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;
import static no.kdrs.grouse.utils.Constants.*;

/**
 * Created by tsodring on 9/8/17.
 */

@Entity
@Table(name = TEMPLATE_REQUIREMENT_TABLE_NAME)
@XmlRootElement(name = "requirement")
@XmlAccessorType(XmlAccessType.FIELD)
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
    @Column(name = "show_order")
    private Integer showOrder;

    /**
     * requirementText (no:tekst)*
     */
    @XmlElement
    @Column(name = "requirement_text", length = 4000)
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
    @Column(name = "priority")
    private String priority;

    /**
     * requirement (no:kravnr)
     * An actual requirement number from the standard
     * e.g 5.2.1
     */
    @XmlElement
    @Column(name = "noark_requirement_number")
    private String noarkRequirementNumber;

    @XmlElement
    private Boolean noarkRequirement;

    @XmlElement
    private String noarkRequirementType;

    @Version
    @Column(name = VERSION)
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "functionality",
            referencedColumnName = "functionality_number")
    private TemplateFunctionality referenceFunctionality;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "template_number",
            referencedColumnName = "template_id")
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

    public String getNoarkRequirementNumber() {
        return noarkRequirementNumber;
    }

    public void setNoarkRequirementNumber(String noarkRequirementNumber) {
        this.noarkRequirementNumber = noarkRequirementNumber;
    }

    public Boolean getNoarkRequirement() {
        return noarkRequirement;
    }

    public void setNoarkRequirement(Boolean noarkRequirement) {
        this.noarkRequirement = noarkRequirement;
    }

    public String getNoarkRequirementType() {
        return noarkRequirementType;
    }

    public void setNoarkRequirementType(String noarkRequirementType) {
        this.noarkRequirementType = noarkRequirementType;
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
                ", noarkRequirementNumber='" + noarkRequirementNumber + '\'' +
                ", noarkRequirement=" + noarkRequirement +
                ", noarkRequirementType='" + noarkRequirementType + '\'' +
                '}';
    }
}
