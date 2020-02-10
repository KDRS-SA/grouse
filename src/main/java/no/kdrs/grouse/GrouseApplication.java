package no.kdrs.grouse;

import no.kdrs.grouse.model.Requirement;
import no.kdrs.grouse.model.TemplateFunctionality;
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

@SpringBootApplication
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

	        // Only import if there is no data
            if(functionalityRepository.count() > 0)
                return;

            JAXBContext jaxbContext = JAXBContext.newInstance(Chapters.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            InputStream data = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("db" + File.separator + "data.xml");

            Chapters chapters = (Chapters) jaxbUnmarshaller.unmarshal(data);


            // Create a "root" parent in the database, that everyone is a child
            // to
            TemplateFunctionality parentTemplateFunctionality = new
                    TemplateFunctionality.FunctionalityBuilder()
                    .id("0")
                    .sectionTitle("Kravspec")
                    .build();
            functionalityRepository.save(parentTemplateFunctionality);

            Integer chapterCount = 0;
            for (Chapter chapter : chapters.getChapters()) {
                chapterCount++;
                Integer sectionCount = 0;
                TemplateFunctionality localParentTemplateFunctionality = parentTemplateFunctionality;

                // Handle the chapter description of functionality
                TemplateFunctionality chapterTemplateFunctionality = new
                        TemplateFunctionality.FunctionalityBuilder()
                        .id(chapterCount.toString())
                        .sectionTitle(chapter.getChapterTitle())
                        .type("mainmenu")
                        .showMe(chapter.getShowMe())
                        .build();

                chapterTemplateFunctionality.setReferenceParentTemplateFunctionality(
                        parentTemplateFunctionality);
                functionalityRepository.save(chapterTemplateFunctionality);

                for (Section section : chapter.getSections()) {
                    sectionCount++;
                    TemplateFunctionality templateFunctionality = new
                            TemplateFunctionality.FunctionalityBuilder()
                            .id(chapterCount.toString() + "." +
                                    sectionCount.toString())
                            .sectionTitle(section.getSectionTitle())
                            .sectionOrder(section.getSectionOrder())
                            .type(section.getType())
                            .description(section.getDescription())
                            .consequence(section.getConsequence())
                            .explanation(section.getExplanation())
                            .showMe(section.getShowMe())
                            .build();

                    templateFunctionality.setReferenceParentTemplateFunctionality(
                            chapterTemplateFunctionality);
                    functionalityRepository.save(templateFunctionality);

                    Requirements requirements = section.getRequirements();

                    if (requirements != null) {
                        ArrayList<Requirement> allRequirements = (ArrayList)
                                requirements.getRequirement();

                        for (Requirement requirement : allRequirements) {
                            System.out.println(requirement.toString());

                            requirement.setFunctionality(templateFunctionality);
                            requirementRepository.save(requirement);
                        }
                    }

                    ArrayList<Section> childSections = (ArrayList)
                            section.getSections();

                    Integer subSectionCount = -1;
                    for (Section childSection: childSections) {
                        subSectionCount++;
                        TemplateFunctionality childTemplateFunctionality = new
                                TemplateFunctionality.FunctionalityBuilder()
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

                        childTemplateFunctionality.setReferenceParentTemplateFunctionality(
                                templateFunctionality);
                        functionalityRepository.save(childTemplateFunctionality);

                        Requirements childRequirements = childSection
                                .getRequirements();

                        if (childRequirements != null) {
                            ArrayList<Requirement> allRequirements = (ArrayList)
                                    childRequirements.getRequirement();

                            for (Requirement requirement : allRequirements) {
                                System.out.println(requirement.toString());

                                requirement.setFunctionality(childTemplateFunctionality);
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
