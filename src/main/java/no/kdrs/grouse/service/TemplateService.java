package no.kdrs.grouse.service;

import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.persistence.ITemplateFunctionalityRepository;
import no.kdrs.grouse.persistence.ITemplateRepository;
import no.kdrs.grouse.persistence.ITemplateRequirementRepository;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import no.kdrs.grouse.utils.PatchObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by tsodring on 9/25/17.
 */
@Service
@Transactional
public class TemplateService
        extends GrouseService
        implements ITemplateService {

    private static final Logger logger =
            LoggerFactory.getLogger(TemplateService.class);

    private EntityManager entityManager;
    private ITemplateRepository templateRepository;
    private ITemplateRequirementRepository requirementRepository;
    private ITemplateFunctionalityRepository functionalityRepository;
    private ITemplateRequirementRepository templateRequirementRepository;
    private ITemplateFunctionalityRepository templateFunctionalityRepository;

    // Columns that it is possible to update via a PATCH request
    private ArrayList<String> allowableColumns =
            new ArrayList<String>(Arrays.asList("templateName",
                    "fileNameInternal", "ownedBy"));

    public TemplateService(
            EntityManager entityManager,
            ITemplateRepository templateRepository,
            ITemplateRequirementRepository requirementRepository,
            ITemplateFunctionalityRepository functionalityRepository,
            ITemplateRequirementRepository templateRequirementRepository,
            ITemplateFunctionalityRepository templateFunctionalityRepository) {
        this.entityManager = entityManager;
        this.templateRepository = templateRepository;
        this.requirementRepository = requirementRepository;
        this.functionalityRepository = functionalityRepository;
        this.templateRequirementRepository = templateRequirementRepository;
        this.templateFunctionalityRepository = templateFunctionalityRepository;
    }

//    @Override
@SuppressWarnings("unchecked")
public Page<TemplateRequirement> findByTemplateIdOrderByTemplateName(
        Pageable page, UUID templateId, String functionalityNumber) {

    return templateRequirementRepository.findByFunctionalityFunctionalityNumber(
            functionalityNumber);
}

    /**
     * findFunctionalityForTemplate
     * <p>
     * Get a list of TemplateFunctionality for a give Template
     *
     * @param templateId Id of the template to retrieve TemplateFunctionality
     * @return list of TemplateFunctionality
     */
    //@Override
    public List<TemplateFunctionality> findFunctionalityForTemplateByType(
            Pageable pageable, UUID templateId, String type) {


        /*
        revisit this!
        return templateFunctionalityRepository.
                findByReferenceTemplateAndTypeAndShowMe(
                        getTemplateOrThrow(templateId), type, true);

         */
        return null;
    }

    @Override
    public void createFunctionality(
            @NotNull final UUID templateId,
            TemplateFunctionality templateFunctionality) {
        Template template = getTemplateOrThrow(templateId);
        template.addTemplateFunctionality(templateFunctionality);
        templateFunctionality.setReferenceTemplate(template);
        templateFunctionalityRepository.save(templateFunctionality);
    }

    @Override
    public Page<Template> findAll(Pageable page) {
        return templateRepository.findAll(page);
    }

    @Override
    public Template findById(@NotNull UUID id) {
        return getTemplateOrThrow(id);
    }

    /**
     * Create a new template.
     * <p>
     * The following steps are performed:
     * 1. Retrieve GrouseUser object from loggedin user and associate with
     * template
     *
     * @param template The template object to create
     * @return The persisted object after it was persisted with associated data
     */
    @Override
    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    @Override
    public Template update(UUID id, PatchObjects patchObjects)
            throws EntityNotFoundException {
        return (Template) handlePatch(getTemplateOrThrow(id), patchObjects);
    }

    @Override
    public List<Template> findByOwnedBy(String ownedBy) {
        return templateRepository.findByOwnedBy(ownedBy);
    }

    /**
     * delete the template identified by the id. Find all children related to
     * the template. These are templateRequirements and templateFunctionality.
     * These are deleted first before the template is deleted
     *
     * @param id the id of the template ot delete
     */
    @Override
    public void delete(UUID id) {
        Template template = getTemplateOrThrow(id);
        /* revisit this
        for (TemplateRequirement templateRequirement :
                template.getReferenceTemplateRequiremnt()) {
            templateRequirementRepository.delete(templateRequirement);
        }

         */
        for (TemplateFunctionality templateFunctionality :
                template.getReferenceTemplateFunctionality()) {
            templateFunctionalityRepository.delete(templateFunctionality);
        }
        templateRepository.delete(template);
    }

    @Override
    protected boolean checkColumnUpdatable(String path) {
        return allowableColumns.contains(path);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid Template back. If there is no valid
     * Template, a EntityNotFoundException exception is thrown
     *
     * @param id The systemId of the template object to retrieve
     * @return the template object
     */
    private Template getTemplateOrThrow(@NotNull UUID id)
            throws EntityNotFoundException {
        return templateRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No Template exists with Id " + id));
    }
}
