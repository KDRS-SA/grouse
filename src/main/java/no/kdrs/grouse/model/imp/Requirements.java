package no.kdrs.grouse.model.imp;

import no.kdrs.grouse.model.Requirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "requirements")
@XmlAccessorType(XmlAccessType.FIELD)
public class Requirements {

    @XmlElement(name = "requirement")
    private List<Requirement> requirement = new ArrayList<>();

    public List<Requirement> getRequirement() {
        return requirement;
    }

    public void setRequirement(List<Requirement> requirement) {
        this.requirement = requirement;
    }
}
