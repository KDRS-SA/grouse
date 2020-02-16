package no.kdrs.grouse.service;

import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.persistence.ITemplateFunctionalityRepository;
import no.kdrs.grouse.persistence.ITemplateRepository;
import no.kdrs.grouse.persistence.ITemplateRequirementRepository;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
@Service
@Transactional
public class TemplateService
        extends GrouseService
        implements ITemplateService {

    private EntityManager entityManager;
    private ITemplateRepository templateRepository;
    private ITemplateRequirementRepository requirementRepository;
    private ITemplateFunctionalityRepository functionalityRepository;
    private ITemplateRequirementRepository templateRequirementRepository;
    private ITemplateFunctionalityRepository templateFunctionalityRepository;

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

    @Override
    @SuppressWarnings("unchecked")
    public List<TemplateRequirement> findByTemplateIdOrderByTemplateName(
            Long templateId, String functionalityNumber) {
        String queryString =
                "select p from TemplateRequirement as p where " +
                        "p.referenceTemplate.templateId = :templateId " +
                        "AND p.referenceFunctionality.functionalityNumber = " +
                        ":functionalityNumber";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("templateId", templateId);
        query.setParameter("functionalityNumber", functionalityNumber);
        return query.getResultList();
    }

    /**
     * findFunctionalityForTemplate
     * <p>
     * Get a list of TemplateFunctionality for a give Template
     *
     * @param templateId Id of the template to retrieve TemplateFunctionality
     * @return list of TemplateFunctionality
     */
    @Override
    public List<TemplateFunctionality> findFunctionalityForTemplateByType(
            Long templateId, String type) {


        /*
        revisit this!
        return templateFunctionalityRepository.
                findByReferenceTemplateAndTypeAndShowMe(
                        getTemplateOrThrow(templateId), type, true);

         */
        return null;
    }

    @Override
    public List<Template> findAll() {
        return (List<Template>) templateRepository.findAll();
    }

    @Override
    public Template findById(@NotNull Long id) {
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
    public Template update(Long id, Template template)
            throws EntityNotFoundException {

        Template originalTemplate = getTemplateOrThrow(id);
        // copy the values over
        originalTemplate.setTemplateName(template.getTemplateName());

        // probably don't want to expose this one
        //originalTemplate.ListTemplateOwner(template.getTemplateOwner());
        return originalTemplate;
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
    public void delete(Long id) {
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

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid Template back. If there is no valid
     * Template, a EntityNotFoundException exception is thrown
     *
     * @param id The systemId of the template object to retrieve
     * @return the template object
     */
    private Template getTemplateOrThrow(@NotNull Long id)
            throws EntityNotFoundException {
        return templateRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No Template exists with Id " + id));
    }
}
