package no.kdrs.grouse.model.imp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "section")
@XmlAccessorType (XmlAccessType.FIELD)
public class Section {

    @XmlElement(name = "sectionTitle")
    private String sectionTitle;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "explanation")
    private String explanation;

    @XmlElement(name = "consequence")
    private String consequence;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "sectionOrder")
    private Integer sectionOrder;

    @XmlElement(name = "showMe")
    private Boolean showMe;

    @XmlElement(name = "requirements")
    private Requirements requirements;

    @XmlElement(name = "section")
    private List<Section> sections = new ArrayList<>();

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}
