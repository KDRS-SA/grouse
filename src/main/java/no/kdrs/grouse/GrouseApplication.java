package no.kdrs.grouse;

import no.kdrs.grouse.model.Functionality;
import no.kdrs.grouse.model.Requirement;
import no.kdrs.grouse.model.imp.Chapter;
import no.kdrs.grouse.model.imp.Chapters;
import no.kdrs.grouse.model.imp.Requirements;
import no.kdrs.grouse.model.imp.Section;
import no.kdrs.grouse.persistence.IFunctionalityRepository;
import no.kdrs.grouse.persistence.IRequirementRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

@SpringBootApplication()
public class GrouseApplication {

    private IFunctionalityRepository functionalityRepository;
    private IRequirementRepository requirementRepository;

    public GrouseApplication(
            IFunctionalityRepository functionalityRepository,
            IRequirementRepository requirementRepository) {
        this.functionalityRepository = functionalityRepository;
        this.requirementRepository = requirementRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(GrouseApplication.class, args);
	}

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {

	    try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Chapters.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            InputStream data = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("db" + File.separator + "data.xml");

            Chapters chapters = (Chapters) jaxbUnmarshaller.unmarshal(data);

            Functionality parentFunctionality = new
                    Functionality.FunctionalityBuilder()
                    .id("0")
                    .sectionTitle("Kravspec")
                    .build();
            functionalityRepository.save(parentFunctionality);

            Integer chapterCount = 0;
            for (Chapter chapter : chapters.getChapters()) {
                chapterCount++;
                Integer sectionCount = -1;
                for (Section section: chapter.getSections()) {
                    String sectionTitle = section.getSectionTitle();
                    sectionCount++;
                    Functionality functionality = new
                            Functionality.FunctionalityBuilder()
                            .id(chapterCount.toString() + "." +
                                    sectionCount.toString())
                            .sectionTitle(section.getSectionTitle())
                            .description(section.getDescription())
                            //.functionalityNumber(section.get)
                            .consequence(section.getConsequence())
                            .explanation(section.getExplanation())
                            .showMe(section.getShowMe())
                            .build();

                    functionality.setReferenceParentFunctionality(
                            parentFunctionality);
                    functionalityRepository.save(functionality);

                    Requirements requirements = section.getRequirements();

                    if (requirements != null) {
                        ArrayList<Requirement> allRequirements = (ArrayList)
                                requirements.getRequirement();

                        for (Requirement requirement : allRequirements) {
                            System.out.println(requirement.toString());

                            requirement.setFunctionality(functionality);
                            requirementRepository.save(requirement);
                        }
                    }

                    ArrayList<Section> childSections= (ArrayList)
                            section.getSections();

                    Integer subSectionCount = -1;
                    for (Section childSection: childSections) {
                        subSectionCount++;
                        Functionality childFunctionality = new
                                Functionality.FunctionalityBuilder()
                                .id(chapterCount.toString() + "." +
                                        sectionCount.toString() + "." +
                                        subSectionCount.toString())
                                .sectionTitle(childSection.getSectionTitle())
                                .description(childSection.getDescription())
                                //.functionalityNumber(section.get)
                                .consequence(childSection.getConsequence())
                                .explanation(childSection.getExplanation())
                                .showMe(childSection.getShowMe())
                                .build();

                        childFunctionality.setReferenceParentFunctionality(
                                functionality);
                        functionalityRepository.save(childFunctionality);

                        Requirements childRequirements = childSection
                                .getRequirements();

                        if (childRequirements != null) {
                            ArrayList<Requirement> allRequirements = (ArrayList)
                                    childRequirements.getRequirement();

                            for (Requirement requirement : allRequirements) {
                                System.out.println(requirement.toString());

                                requirement.setFunctionality(childFunctionality);
                                requirementRepository.save(requirement);
                            }
                        }

                    }
                }
            }
        }
        catch (JAXBException e) {
	        System.out.println(e.toString());
        }
	}
}
