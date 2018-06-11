package no.kdrs.grouse.model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by tsodring on 9/8/17.
 */

@Entity
@Table(name = "requirements")
@XmlRootElement(name = "requirement")
@XmlAccessorType(XmlAccessType.FIELD)
public class Requirement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

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
     *
      * An assigned values as to how important this is in the requirements
      * specifcation
     * e.g.
     *   O - Obligatorisk
     *   1 - Svært viktig for oppdragsgiver
     *   2 - Viktig for oppdragsgiver
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "functionality",
            referencedColumnName = "functionality_number")
    private Functionality referenceFunctionality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Functionality getFunctionality() {
        return referenceFunctionality;
    }

    public void setFunctionality(Functionality referenceFunctionality) {
        this.referenceFunctionality = referenceFunctionality;
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

    @Override
    public String toString() {
        return "SRequirement{" +
                "id=" + id +
                ", showOrder=" + showOrder +
                ", requirementText='" + requirementText + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
