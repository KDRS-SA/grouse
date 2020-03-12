package no.kdrs.grouse.spring;

import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.model.imp.*;
import no.kdrs.grouse.persistence.ITemplateFunctionalityRepository;
import no.kdrs.grouse.persistence.ITemplateRepository;
import no.kdrs.grouse.persistence.ITemplateRequirementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static no.kdrs.grouse.utils.Constants.RESOURCE_TEMPLATES;

/**
 * Import datasets to database. Note import will occur as long as there is no
 * profile 'do-not-import-templates' active. However, if the database contains
 * a template with the same name, then the template will not be be imported.
 */
@Configuration
public class AfterStartup {

    private static final Logger logger =
            LoggerFactory.getLogger(AfterStartup.class);

    private ITemplateFunctionalityRepository functionalityRepository;
    private ITemplateRequirementRepository requirementRepository;
    private ITemplateRepository templateRepository;

    private Environment environment;

    public AfterStartup(
            Environment environment,
            ITemplateRepository templateRepository,
            ITemplateFunctionalityRepository functionalityRepository,
            ITemplateRequirementRepository requirementRepository) {
        this.environment = environment;
        this.templateRepository = templateRepository;
        this.functionalityRepository = functionalityRepository;
        this.requirementRepository = requirementRepository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            if (!Arrays.asList(environment.getActiveProfiles())
                    .contains("do-not-import-templates")) {
                PathMatchingResourcePatternResolver resolver =
                        new PathMatchingResourcePatternResolver();
                for (Resource resource :
                        resolver.getResources(RESOURCE_TEMPLATES)) {
                    importTemplate(resource);
                }
            }
        } catch (JAXBException | IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Currently, we assume that the template XML document is not large enough
     * to cause problems with a DOM approach.
     *
     * @param resource
     * @throws JAXBException
     * @throws IOException
     */
    private void importTemplate(Resource resource)
            throws JAXBException, IOException {

        JAXBContext jaxbContext = JAXBContext.newInstance(ImpTemplate.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        InputStream templateStream = new FileInputStream(resource.getFile());

        ImpTemplate impTemplate = (ImpTemplate)
                jaxbUnmarshaller.unmarshal(templateStream);

        // Do nothing if the template has already been imported. Note updates
        // to templates in XML have to be manually updated in the database.
        // Currently we do not support an automatic update approach. It may
        // come later if it is required.

        if (templateRepository
                .findById(impTemplate.getTemplateId())
                .isEmpty()) {
            Template template = new Template();
            template.setTemplateId(impTemplate.getTemplateId());
            template.setTemplateName(impTemplate.getName());
            template.setDescription(impTemplate.getDescription());
            template.setArea(impTemplate.getArea());
            template.setType(impTemplate.getType());

            Chapters chapters = impTemplate.getChapters();
            // Create a "root" functionality that all functionality in this
            // template can be traced back to.
            TemplateFunctionality parentTemplateFunctionality = new
                    TemplateFunctionality.FunctionalityBuilder()
                    .sectionTitle("Requirements")
                    .functionalityNumber("0")
                    .build();

            parentTemplateFunctionality.setReferenceTemplate(
                    templateRepository.save(template));

            functionalityRepository.save(parentTemplateFunctionality);

            Integer chapterCount = 0;
            for (Chapter chapter : chapters.getChapters()) {
                chapterCount++;
                Integer sectionCount = 0;
                TemplateFunctionality localParentTemplateFunctionality = parentTemplateFunctionality;

                // Handle the chapter description of functionality
                TemplateFunctionality chapterTemplateFunctionality = new
                        TemplateFunctionality.FunctionalityBuilder()
                        .functionalityNumber(chapterCount.toString())
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
                            .functionalityNumber(chapterCount.toString()
                                    + "." + sectionCount.toString())
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
                                .functionalityNumber(
                                        chapterCount.toString() + "." +
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
        } else {
            logger.info("Not importing " + resource.getFilename());
        }
    }
}
