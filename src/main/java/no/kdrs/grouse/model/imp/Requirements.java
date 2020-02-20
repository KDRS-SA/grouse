package no.kdrs.grouse.model.imp;

import no.kdrs.grouse.model.TemplateRequirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "requirements")
@XmlAccessorType(XmlAccessType.FIELD)
public class Requirements {

    @XmlElement(name = "templateRequirement")
    private List<TemplateRequirement> templateRequirement = new ArrayList<>();

    public List<TemplateRequirement> getTemplateRequirement() {
        return templateRequirement;
    }

    public void setTemplateRequirement(List<TemplateRequirement> templateRequirement) {
        this.templateRequirement = templateRequirement;
    }
}
