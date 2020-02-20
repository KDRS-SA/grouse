package no.kdrs.grouse.service;

import no.kdrs.grouse.model.TemplateRequirement;
import no.kdrs.grouse.persistence.ITemplateRequirementRepository;
import no.kdrs.grouse.service.interfaces.ITemplateRequirementService;
import no.kdrs.grouse.utils.PatchObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Transactional
public class TemplateRequirementService
        extends GrouseService
        implements ITemplateRequirementService {

    private static final Logger logger =
            LoggerFactory.getLogger(TemplateRequirementService.class);

    private ITemplateRequirementRepository requirementRepository;

    public TemplateRequirementService(
            ITemplateRequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    public List<TemplateRequirement> findAll() {
        return (List<TemplateRequirement>) requirementRepository.findAll();
    }

    @Override
    public TemplateRequirement findById(Long id) {
        return getRequirementOrThrow(id);
    }

    @Override
    public TemplateRequirement save(TemplateRequirement TemplateRequirement) {
        return requirementRepository.save(TemplateRequirement);
    }

    @Override
    public TemplateRequirement updateTemplateRequirement(
            PatchObjects patchObjects, Long requirementNumber) {
        return (TemplateRequirement)
                handlePatch(getRequirementOrThrow(requirementNumber),
                        patchObjects);
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
