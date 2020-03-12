package no.kdrs.grouse.model.imp;

import no.kdrs.grouse.model.TemplateRequirement;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "requirements")
@XmlAccessorType(FIELD)
public class Requirements {

    @XmlElement(name = "requirement")
    private List<TemplateRequirement> templateRequirement = new ArrayList<>();

    public List<TemplateRequirement> getTemplateRequirement() {
        return templateRequirement;
    }

    public void setTemplateRequirement(List<TemplateRequirement> templateRequirement) {
        this.templateRequirement = templateRequirement;
    }
}
