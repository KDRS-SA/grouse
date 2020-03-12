package no.kdrs.grouse.spring;

import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.model.imp.Chapter;
import no.kdrs.grouse.model.imp.ImpTemplate;
import no.kdrs.grouse.model.imp.Section;
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
import java.util.Arrays;
import java.util.List;

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
        logger.debug(event.toString());
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
     * @param resource A Resource file from the resources directory
     * @throws JAXBException there is a problem with XML
     * @throws IOException   there is a problem reading file
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
            // Create a default template object from the header of the
            // template file
            Template template = new Template();
            template.setTemplateId(impTemplate.getTemplateId());
            template.setTemplateName(impTemplate.getName());
            template.setDescription(impTemplate.getDescription());
            template.setArea(impTemplate.getArea());
            template.setType(impTemplate.getType());

            // Create a "root" functionality that all functionality in this
            // template can be traced back to.
            TemplateFunctionality rootTemplateFunctionality = new
                    TemplateFunctionality.FunctionalityBuilder()
                    .sectionTitle("Requirements")
                    .functionalityNumber("0")
                    .build();

            rootTemplateFunctionality.setReferenceTemplate(
                    templateRepository.save(template));
            rootTemplateFunctionality =
                    functionalityRepository.save(rootTemplateFunctionality);

            int chapterCount = 1;
            for (Chapter chapter : impTemplate.getChapters()) {
                // Handle the chapter description of functionality
                TemplateFunctionality templateFunctionality = new
                        TemplateFunctionality.FunctionalityBuilder()
                        .functionalityNumber(Integer.toString(chapterCount++))
                        .sectionTitle(chapter.getChapterTitle())
                        .type("mainmenu")
                        .showMe(chapter.getShowMe())
                        .build();

                templateFunctionality
                        .setReferenceParentTemplateFunctionality(
                                rootTemplateFunctionality);
                templateFunctionality =
                        functionalityRepository.save(templateFunctionality);

                processFunctionality(templateFunctionality,
                        chapter.getSections(), Integer.toString(chapterCount));
            }
        } else {
            logger.info("Not importing " + resource.getFilename());
        }
    }

    private void processFunctionality(
            TemplateFunctionality parentFunctionality, List<Section> sections,
            String count) {
        int sectionCount = 0;
        for (Section section : sections) {
            String sectionId = count + "." + sectionCount++;
            TemplateFunctionality templateFunctionality = new
                    TemplateFunctionality.FunctionalityBuilder()
                    .functionalityNumber(sectionId)
                    .sectionTitle(section.getSectionTitle())
                    .sectionOrder(section.getSectionOrder())
                    .type(section.getType())
                    .description(section.getDescription())
                    .consequence(section.getConsequence())
                    .explanation(section.getExplanation())
                    .showMe(section.getShowMe())
                    .build();
            templateFunctionality.setReferenceParentTemplateFunctionality(
                    parentFunctionality);
            templateFunctionality =
                    functionalityRepository.save(templateFunctionality);
            if (section.getRequirements() != null) {
                processRequirements(templateFunctionality,
                        section.getRequirements().getTemplateRequirement());
            }
            processFunctionality(templateFunctionality, section.getSections(),
                    sectionId);
        }
    }

    private void processRequirements(
            TemplateFunctionality templateFunctionality,
            List<TemplateRequirement> templateRequirements) {
        for (TemplateRequirement templateRequirement : templateRequirements) {
            templateRequirement.setFunctionality(templateFunctionality);
            requirementRepository.save(templateRequirement);
        }
    }
}
