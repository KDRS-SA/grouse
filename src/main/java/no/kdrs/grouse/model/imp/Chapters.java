package no.kdrs.grouse.model.imp;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "chapters")
@XmlAccessorType(FIELD)
public class Chapters {

    @XmlElement(name = "chapter")
    private List<Chapter> chapters = new ArrayList<>();

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
