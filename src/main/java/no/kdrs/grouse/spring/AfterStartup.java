package no.kdrs.grouse.spring;

import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.model.imp.Chapter;
import no.kdrs.grouse.model.imp.Chapters;
import no.kdrs.grouse.model.imp.Requirements;
import no.kdrs.grouse.model.imp.Section;
import no.kdrs.grouse.persistence.ITemplateFunctionalityRepository;
import no.kdrs.grouse.persistence.ITemplateRequirementRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Import datasets to database. Note import will occur as long as there is no
 * profile 'do-not-import-datasets' active. However, if the database contains
 * a template with the same name, then the template will not be be imported.
 */
@Configuration
public class AfterStartup {

    private ITemplateFunctionalityRepository functionalityRepository;
    private ITemplateRequirementRepository requirementRepository;
    private Environment environment;

    public AfterStartup(
            Environment environment,
            ITemplateFunctionalityRepository functionalityRepository,
            ITemplateRequirementRepository requirementRepository) {
        this.functionalityRepository = functionalityRepository;
        this.requirementRepository = requirementRepository;
        this.environment = environment;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            if (!Arrays.asList(
                    environment.getActiveProfiles())
                    .contains("do-not-import-datasets")) {
                // Only import if there is no data
                if (functionalityRepository.count() > 0)
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
                            ArrayList<TemplateRequirement> allTemplateRequirements =
                                    (ArrayList<TemplateRequirement>)
                                            requirements.getTemplateRequirement();

                            for (TemplateRequirement templateRequirement : allTemplateRequirements) {
                                System.out.println(templateRequirement.toString());

                                templateRequirement.setFunctionality(templateFunctionality);
                                requirementRepository.save(templateRequirement);
                            }
                        }

                        ArrayList<Section> childSections =
                                (ArrayList<Section>) section.getSections();

                        Integer subSectionCount = -1;
                        for (Section childSection : childSections) {
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
                                ArrayList<TemplateRequirement>
                                        allTemplateRequirements =
                                        (ArrayList<TemplateRequirement>)
                                                childRequirements.getTemplateRequirement();

                                for (TemplateRequirement templateRequirement : allTemplateRequirements) {
                                    System.out.println(templateRequirement.toString());

                                    templateRequirement.setFunctionality(childTemplateFunctionality);
                                    requirementRepository.save(templateRequirement);
                                }
                            }

                        }
                    }
                }
            }
        } catch (JAXBException e) {
            System.out.println(e.toString());
        }
    }
}
