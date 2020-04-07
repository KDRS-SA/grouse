package no.kdrs.grouse.service;

import no.kdrs.grouse.model.TemplateFunctionality;
import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.persistence.ITemplateFunctionalityRepository;
import no.kdrs.grouse.persistence.ITemplateRequirementRepository;
import no.kdrs.grouse.service.interfaces.ITemplateFunctionalityService;
import no.kdrs.grouse.utils.PatchObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
public class TemplateFunctionalityService
        extends GrouseService
        implements ITemplateFunctionalityService {

    private static final Logger logger =
            LoggerFactory.getLogger(TemplateFunctionalityService.class);

    private ITemplateRequirementRepository templateRequirementRepository;
    private ITemplateFunctionalityRepository templateFunctionalityRepository;

    // Columns that it is possible to update via a PATCH request
    private ArrayList<String> allowableColumns =
            new ArrayList<>(Arrays.asList("title", "processed", "ownedBy",
                    "functionalityNumber"));

    public TemplateFunctionalityService(ITemplateRequirementRepository
                                                templateRequirementRepository,
                                        ITemplateFunctionalityRepository
                                                templateFunctionalityRepository) {
        this.templateRequirementRepository = templateRequirementRepository;
        this.templateFunctionalityRepository = templateFunctionalityRepository;
    }

    @Override
    public TemplateFunctionality getTemplateFunctionality(
            Long templateFunctionalityId) {
        return getTemplateFunctionalityOrThrow(templateFunctionalityId);
    }

    @Override
    public Page<TemplateFunctionality> getChildFunctionality(
            Pageable pageable, Long templateFunctionalityId) {
        return templateFunctionalityRepository
                .findByReferenceParentFunctionality(pageable,
                        getTemplateFunctionalityOrThrow(templateFunctionalityId));
    }

    @Override
    public Page<TemplateRequirement> getRequirements(
            Pageable pageable, Long templateFunctionalityId) {
        return templateRequirementRepository
                .findByReferenceFunctionality(
                        getTemplateFunctionalityOrThrow(templateFunctionalityId),
                        pageable);
    }

    @Override
    public TemplateRequirement createTemplateRequirement(
            Long templateFunctionalityId, TemplateRequirement templateRequirement) {

        Optional<TemplateFunctionality> templateFunctionality =
                templateFunctionalityRepository.findById(templateFunctionalityId);
        if (templateFunctionality.isPresent()) {
            String loggedInUser = SecurityContextHolder.getContext().getAuthentication()
                    .getName();
            templateRequirement.setOwnedBy(loggedInUser);
            templateRequirement.setFunctionality(
                    templateFunctionality.get());
        } else {
            throw new EntityNotFoundException(
                    "Cannot find TemplateFunctionality [" +
                            templateFunctionalityId + "]");
        }

        return templateRequirementRepository.save(templateRequirement);
    }


    @Override
    public TemplateFunctionality updateTemplateFunctionality(
            PatchObjects patchObjects, Long requirementNumber) {
        return (TemplateFunctionality)
                handlePatch(getTemplateFunctionalityOrThrow(requirementNumber),
                        patchObjects);
    }

    @Override
    public void delete(Long functionalityID) {
        templateFunctionalityRepository.deleteById(functionalityID);
    }

    @Override
    protected boolean checkColumnUpdatable(String path) {
        return allowableColumns.contains(path);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid Template back. If there is no valid
     * TemplateRequirement, a EntityNotFoundException exception is thrown
     * <p>
     * We also check that you only can access things you own. If you try
     * to access an object you don't own, then a AccessDeniedException is thrown.
     *
     * @param id The id  of the TemplateRequirement object to retrieve
     * @return the TemplateRequirement object
     */
    private TemplateFunctionality getTemplateFunctionalityOrThrow(
            @NotNull Long id)
            throws EntityNotFoundException, AccessDeniedException {
        TemplateFunctionality templateFunctionality =
                templateFunctionalityRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "No TemplateRequirement exists with Id "
                                                + id));
        return templateFunctionality;
    }
}
