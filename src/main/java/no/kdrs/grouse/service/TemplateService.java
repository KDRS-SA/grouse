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
     * 2. Copy all SRequirement objects and create TemplateRequirement objects
     * 3. Copy all TemplateFunctionality objects and create
     * TemplateFunctionality objects
     * <p>
     * This is done as each template needs their own copy to work on.
     * Requirements are needed as within the template the used can change the
     * text, add or remove TemplateRequirements
     * TemplateFunctionality are needed to save state of the progress. For each
     * step that is processed a progress value is set. This is useful to be
     * able to reload the template but also eases the GUI side of things.
     * <p>
     * State is stored in the database, not the GUI
     *
     * @param template The template object to create
     * @return The persisted object after it was persisted with associated data
     */
    @Override
    public Template createTemplate(Template template) {

        return template;

        /*

        Need to revisit this!

        String ownedBy = getUser();
        templateRepository.save(template);

        ArrayList<TemplateFunctionality> functionalities =
                (ArrayList<TemplateFunctionality>)
                functionalityRepository.
                        findAllByOrderById();

        HashMap<String, TemplateFunctionality> parents = new HashMap<>();

        for (TemplateFunctionality templateFunctionality : functionalities) {

            TemplateFunctionality existingTemplateFunctionality
                    = parents.get(templateFunctionality.getFunctionalityNumber());

            if (existingTemplateFunctionality != null) {
                continue;
            }

            TemplateFunctionality newTemplateFunctionality =
                    new TemplateFunctionality();

            newTemplateFunctionality.setFunctionalityNumber(
                    templateFunctionality.getFunctionalityNumber());
            newTemplateFunctionality.setTitle(
                    templateFunctionality.getTitle());
            newTemplateFunctionality.setConsequence(
                    templateFunctionality.getConsequence());
            newTemplateFunctionality.setDescription(
                    templateFunctionality.getDescription());
            newTemplateFunctionality.setExplanation(
                    templateFunctionality.getExplanation());
            newTemplateFunctionality.setType(
                    templateFunctionality.getType());
            newTemplateFunctionality.setShowMe(
                    templateFunctionality.getShowMe());
            newTemplateFunctionality.setReferenceTemplate(template);

            TemplateFunctionality parentTemplateFunctionality =
                    templateFunctionality.getReferenceParentTemplateFunctionality();

            parents.put(templateFunctionality.getFunctionalityNumber(),
                    templateFunctionality);

            // The root contains a null parent, avoids null pointer
            if (parentTemplateFunctionality != null) {

                // Everything else has a parent,
                TemplateFunctionality parentTemplateFunctionality =
                        parents.get(templateFunctionality
                                .getReferenceParentTemplateFunctionality().
                                        getFunctionalityNumber());

                // This templateFunctionality sets its parent to the
                // correct / found parent

                newTemplateFunctionality.setReferenceParentTemplateFunctionality(
                        parentTemplateFunctionality);

                // Set the other side of the relationship
                if (parentTemplateFunctionality != null) {
                    parentTemplateFunctionality.
                            addReferenceChildTemplateFunctionality(
                                    templateFunctionality);
                }
            }
            templateFunctionalityRepository.save(templateFunctionality);

            // Check if there are any children that need to be copied

            if (templateFunctionality.getReferenceChildTemplateFunctionality().size() > 0
                    &&
                    // Avoid picking the parent, we only want anything at
                    // level 2
                    templateFunctionality.getReferenceParentTemplateFunctionality() != null) {

                for (TemplateFunctionality childTemplateFunctionality : templateFunctionality
                        .getReferenceChildTemplateFunctionality()) {

                    existingTemplateFunctionality
                            = parents.get(templateFunctionality.getFunctionalityNumber());

                    if (existingTemplateFunctionality != null) {
                        continue;
                    }

                    TemplateFunctionality childTemplateFunctionality =
                            new TemplateFunctionality();

                    childTemplateFunctionality.setFunctionalityNumber(
                            childTemplateFunctionality.getFunctionalityNumber());
                    childTemplateFunctionality.setTitle(
                            childTemplateFunctionality.getTitle());
                    childTemplateFunctionality.setConsequence(
                            childTemplateFunctionality.getConsequence());
                    childTemplateFunctionality.setDescription(
                            childTemplateFunctionality.getDescription());
                    childTemplateFunctionality.setExplanation(
                            childTemplateFunctionality.getExplanation());
                    childTemplateFunctionality.setType(
                            childTemplateFunctionality.getType());
                    childTemplateFunctionality.setShowMe(
                            childTemplateFunctionality.getShowMe());
                    templateFunctionality
                            .addReferenceChildTemplateFunctionality(
                                    childTemplateFunctionality);
                    childTemplateFunctionality.
                            setReferenceParentTemplateFunctionality(
                                    templateFunctionality);

                    parents.put(templateFunctionality.getFunctionalityNumber(),
                            childTemplateFunctionality);

                    templateFunctionalityRepository.save
                            (childTemplateFunctionality);
                }
            }
        }

        ArrayList<TemplateRequirement> templateRequirements =
                (ArrayList<TemplateRequirement>) requirementRepository.findAll();
        for (TemplateRequirement requirement : templateRequirements) {
            TemplateRequirement templateRequirement = new TemplateRequirement();
            templateRequirement.setReferenceTemplate(template);
            templateRequirement.setOrder(requirement.getShowOrder());
            templateRequirement.setPriority(requirement.getPriority());
            templateRequirement.setRequirementText(
                    requirement.getRequirementText());

            TemplateFunctionality templateFunctionality = requirement.getFunctionality();
            String functionalityNumber = templateFunctionality
                    .getFunctionalityNumber();


            List<TemplateFunctionality> templateFunctionalityList =
                    templateFunctionalityRepository.
                            findByFunctionalityNumberAndReferenceTemplate(
                                    functionalityNumber, template);

            TemplateFunctionality templateFunctionality =
                    templateFunctionalityList.get(0);

            templateRequirement.setReferenceFunctionality(
                    templateFunctionality);

            templateFunctionality.
                    addReferenceTemplateRequirement(templateRequirement);

            templateRequirementRepository.save(templateRequirement);
        }
        return template;
        */
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
