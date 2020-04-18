package no.kdrs.grouse.service;

import no.kdrs.grouse.model.*;
import no.kdrs.grouse.persistence.IGrouseUserRepository;
import no.kdrs.grouse.persistence.ITemplateFunctionalityRepository;
import no.kdrs.grouse.persistence.ITemplateRepository;
import no.kdrs.grouse.persistence.ITemplateRequirementRepository;
import no.kdrs.grouse.service.interfaces.ITemplateService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static no.kdrs.grouse.utils.Constants.TEMPLATE;

@Service
@Transactional
public class TemplateService
        extends GrouseService
        implements ITemplateService {

    private ITemplateRepository templateRepository;
    private ITemplateRequirementRepository templateRequirementRepository;
    private ITemplateFunctionalityRepository templateFunctionalityRepository;
    private IGrouseUserRepository userRepository;

    // Columns that it is possible to update via a PATCH request
    private ArrayList<String> allowableColumns =
            new ArrayList<>(Arrays.asList("templateName",
                    "fileNameInternal", "ownedBy"));

    public TemplateService(
            ITemplateRepository templateRepository,
            ITemplateRequirementRepository templateRequirementRepository,
            ITemplateFunctionalityRepository templateFunctionalityRepository,
            IGrouseUserRepository userRepository) {
        this.templateRepository = templateRepository;
        this.templateRequirementRepository = templateRequirementRepository;
        this.templateFunctionalityRepository = templateFunctionalityRepository;
        this.userRepository = userRepository;
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
    public Page<TemplateFunctionality> findFunctionalityForTemplate(
            Pageable pageable, UUID templateId) {
        return templateFunctionalityRepository.
                findByReferenceTemplate(getTemplateOrThrow(templateId),
                        pageable);
    }

    @Override
    public TemplateFunctionality createFunctionality(
            @NotNull final UUID templateId,
            TemplateFunctionality templateFunctionality) {
        Template template = getTemplateOrThrow(templateId);
        template.addTemplateFunctionality(templateFunctionality);
        templateFunctionality.setReferenceTemplate(template);
        return templateFunctionalityRepository.save(templateFunctionality);
    }

    @Override
    public Page<Template> findAll(Pageable page) {
        return templateRepository.findAll(page);
    }

    @Override
    public Page<Template> findAllForUser(Pageable page) {
        List<UUID> templateIdList = aclService.getListUUIDs(getUser(), TEMPLATE);
        return templateRepository.findByTemplateIdIn(templateIdList, page);
    }

    @Override
    public Template findById(@NotNull UUID id) {
        Template template = getTemplateOrThrow(id);
        checkAccess(template.getTemplateId());
        return template;
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
        Template template = getTemplateOrThrow(id);
        checkAccess(template.getTemplateId());
        return (Template) handlePatch(template, patchObjects);
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
        checkOwner(template.getOwnedBy(), TEMPLATE);
        deleteFunctionalities(template.getReferenceTemplateFunctionality());
        templateRepository.delete(template);
    }

    protected void deleteFunctionalities(
            List<TemplateFunctionality> templateFunctionalities) {
        for (TemplateFunctionality templateFunctionality :
                templateFunctionalities) {
            if (templateFunctionality
                    .getReferenceTemplateRequirement().size() > 0) {
                deleteRequirements(templateFunctionality
                        .getReferenceTemplateRequirement());
            }
            if (templateFunctionality
                    .getReferenceChildTemplateFunctionality().size() > 0) {
                deleteFunctionalities(templateFunctionality
                        .getReferenceChildTemplateFunctionality());
            }
            templateFunctionality.setReferenceParentFunctionality(null);
            templateFunctionality.setReferenceTemplate(null);
            templateFunctionalityRepository.delete(templateFunctionality);
        }
    }

    protected void deleteRequirements(
            List<TemplateRequirement> templateRequirements) {
        for (TemplateRequirement templateRequirement : templateRequirements) {
            templateRequirementRepository.delete(templateRequirement);
        }
    }

    @Override
    protected boolean checkColumnUpdatable(String path) {
        return allowableColumns.contains(path);
    }

    @Override
    public AccessControl shareTemplate(UUID templateId, String username) {
        Template template = getTemplateOrThrow(templateId);
        checkOwner(template.getOwnedBy(), TEMPLATE);
        AccessControl accessControl = new AccessControl();
        accessControl.setAclId(randomUUID());
        accessControl.setObjectType(TEMPLATE);
        accessControl.setGrouseObject(template.getTemplateId());
        accessControl.setGrouseUser(username);
        return aclService.createACLEntry(template.getTemplateId(), accessControl);
    }

    @Override
    public void deleteTemplateShare(UUID templateId, String username) {
        Template template = getTemplateOrThrow(templateId);
        checkOwner(template.getOwnedBy(), TEMPLATE);
        aclService.deleteACLEntry(template.getTemplateId(), username);
    }

    @Override
    public Page<GrouseUser> getTemplateUsers(UUID templateId, Pageable pageable) {
        List<String> userList = aclService.getListUsers(templateId);
        return userRepository.findByUsernameIn(userList, pageable);
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
