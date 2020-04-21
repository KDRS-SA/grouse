package no.kdrs.grouse.service;

import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.persistence.ITemplateRequirementRepository;
import no.kdrs.grouse.service.interfaces.ITemplateRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Transactional
public class TemplateRequirementService
        extends GrouseService
        implements ITemplateRequirementService {

    // Columns that it is possible to update via a PATCH request
    private final ArrayList<String> allowableColumns =
            new ArrayList<>(Arrays.asList("requirementText", "showOrder",
                    "ownedBy", "priority", "requirementNumber"));

    private final ITemplateRequirementRepository requirementRepository;

    public TemplateRequirementService(
            ITemplateRequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    @Override
    public TemplateRequirement findById(Long id) {
        return getRequirementOrThrow(id);
    }

    @Override
    public TemplateRequirement updateTemplateRequirement(
            PatchObjects patchObjects, Long requirementNumber) {
        TemplateRequirement templateRequirement =
                getRequirementOrThrow(requirementNumber);
        checkAccess(templateRequirement.getReferenceTemplate().getTemplateId());
        return (TemplateRequirement) handlePatch(templateRequirement,
                patchObjects);
    }

    @Override
    protected boolean checkColumnUpdatable(String path) {
        return allowableColumns.contains(path);
    }

    @Override
    public void delete(Long id) {
        requirementRepository.deleteById(id);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid SRequirement back. If there is no
     * valid SRequirement, a EntityNotFoundException exception is thrown
     *
     * @param id The systemId of the requirement object to retrieve
     * @return the requirement object
     */
    private TemplateRequirement getRequirementOrThrow(@NotNull Long id)
            throws EntityNotFoundException {
        return requirementRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No SRequirement exists with Id " + id));
    }
}
