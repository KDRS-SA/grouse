package no.kdrs.grouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Created by tsodring on 9/11/17.
 */

@Entity
@Table(name = "functionality_areas")
@XmlRootElement
public class Functionality implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Number of the functional area. e.g 1.2.3
     *  This is an internal number that the project themselves decides
     *
     */
    @Id
    @Column(name = "functionality_number", nullable = false, updatable = false)
    private String functionalityNumber;

    /**
     * Text of the functionality
     *
     * e.g functionality_number '1.7.2.19' has title
     *    'Krav knyttet til masseoppdatering'
     *
     */
    @Column(name = "title")
    private String title;

    /**
     * Description of functionality
     *
     */
    @Column(name = "description", length = 10000)
    private String description;


    /**
     * Description of consequence of excluding this functionality
     *
     */
    @Column(name = "consequence", length = 10000)
    private String consequence;

    /**
     * Description of consequence of excluding this functionality
     *
     */
    @Column(name = "explanation", length = 10000)
    private String explanation;

    /**
     * Whether or not to be displayed in menu
     *
     */
    @Column(name = "show_me")
    private Boolean showMe;

    /**
     * Type of requirement e.g. 'funksjonell', 'teknisk', 'integrasjon'
     *
     */
    @Column(name = "type")
    private String type;

    // Link to parent Functionality
    @ManyToOne
    @JoinColumn(name="parent")
    private Functionality referenceParentFunctionality;


    public Functionality() {
    }

    private Functionality (FunctionalityBuilder functionalityBuilder) {
        this.functionalityNumber = functionalityBuilder.id;
        this.title = functionalityBuilder.sectionTitle;
        this.description = functionalityBuilder.description;
        this.explanation = functionalityBuilder.explanation;
        this.consequence = functionalityBuilder.consequence;
        this.type = functionalityBuilder.type;
        this.showMe = functionalityBuilder.showMe;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFunctionalityNumber() {
        return functionalityNumber;
    }

    public void setFunctionalityNumber(String functionalityNumber) {
        this.functionalityNumber = functionalityNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Boolean getShowMe() {
        return showMe;
    }

    public void setShowMe(Boolean showMe) {
        this.showMe = showMe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public Functionality getReferenceParentFunctionality() {
        return referenceParentFunctionality;
    }
    @XmlTransient
    public void setReferenceParentFunctionality(Functionality referenceParentFunctionality) {
        this.referenceParentFunctionality = referenceParentFunctionality;
    }

    public static class FunctionalityBuilder {

        private String id;
        private String sectionTitle;
        private String functionalityNumber;
        private String description;
        private String explanation;
        private String consequence;
        private String type;
        private Boolean showMe;

        public FunctionalityBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FunctionalityBuilder sectionTitle(String sectionTitle) {
            this.sectionTitle = sectionTitle;
            return this;
        }

        public FunctionalityBuilder functionalityNumber(String functionalityNumber) {
            this.functionalityNumber = functionalityNumber;
            return this;
        }

        public FunctionalityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FunctionalityBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public FunctionalityBuilder consequence(String consequence) {
            this.consequence = consequence;
            return this;
        }

        public FunctionalityBuilder type(String type) {
            this.type = type;
            return this;
        }

        public FunctionalityBuilder showMe(Boolean showMe) {
            this.showMe = showMe;
            return this;
        }

        public Functionality build() {
            return new Functionality(this);
        }

    }

}
