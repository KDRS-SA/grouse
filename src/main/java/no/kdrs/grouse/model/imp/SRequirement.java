package no.kdrs.grouse.model.imp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "requirement")
@XmlAccessorType(XmlAccessType.FIELD)
public class SRequirement {

    @XmlElement
    private String noarkRequirement;

    @XmlElement
    private String noarkRequirementNumber;

    @XmlElement
    private String noarkRequirementType;

    @XmlElement
    private String showOrder;

    @XmlElement
    private String priority;

    @XmlElement
    private String functionality;

    @XmlElement
    private String requirementText;


    public String getNoarkRequirement() {
        return noarkRequirement;
    }

    public void setNoarkRequirement(String noarkRequirement) {
        this.noarkRequirement = noarkRequirement;
    }

    public String getNoarkRequirementNumber() {
        return noarkRequirementNumber;
    }

    public void setNoarkRequirementNumber(String noarkRequirementNumber) {
        this.noarkRequirementNumber = noarkRequirementNumber;
    }

    public String getNoarkRequirementType() {
        return noarkRequirementType;
    }

    public void setNoarkRequirementType(String noarkRequirementType) {
        this.noarkRequirementType = noarkRequirementType;
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getFunctionality() {
        return functionality;
    }

    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }

    public String getRequirementText() {
        return requirementText;
    }

    public void setRequirementText(String requirementText) {
        this.requirementText = requirementText;
    }

    @Override
    public String toString() {
        return "SRequirement{" +
                "noarkRequirement='" + noarkRequirement + '\'' +
                ", noarkRequirementNumber='" + noarkRequirementNumber + '\'' +
                ", noarkRequirementType='" + noarkRequirementType + '\'' +
                ", showOrder='" + showOrder + '\'' +
                ", priority='" + priority + '\'' +
                ", functionality='" + functionality + '\'' +
                ", requirementText='" + requirementText + '\'' +
                '}';
    }
}
