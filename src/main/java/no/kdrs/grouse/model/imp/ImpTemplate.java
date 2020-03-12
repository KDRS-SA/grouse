package no.kdrs.grouse.model.imp;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/**
 * ImpTemplate (ImportTemplate). Used to read in
 */

@XmlRootElement(name = "template")
@XmlAccessorType(FIELD)
public class ImpTemplate {

    @XmlElement(name = "templateId")
    private UUID templateId;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "area")
    private String area;

    @XmlElement(name = "chapters")
    private Chapters chapters;

    public UUID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Chapters getChapters() {
        return chapters;
    }

    public void setChapters(Chapters chapters) {
        this.chapters = chapters;
    }
}