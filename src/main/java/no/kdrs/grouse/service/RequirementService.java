package no.kdrs.grouse.service;

import no.kdrs.grouse.model.Requirement;
import no.kdrs.grouse.persistence.IRequirementRepository;
import no.kdrs.grouse.service.interfaces.IRequirementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
@Service
@Transactional
public class RequirementService
        implements IRequirementService {

    private IRequirementRepository requirementRepository;

    public RequirementService(IRequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    @SuppressWarnings("unchecked")
    public List<Requirement> findAll() {
        return (ArrayList) requirementRepository.findAll();
    }

    @Override
    public Requirement findById(String id) {
        return getRequirementOrThrow(id);
    }

    @Override
    public Requirement save(Requirement Requirement) {
        return requirementRepository.save(Requirement);
    }

    @Override
    public Requirement update(String id, Requirement requirement)
            throws EntityNotFoundException {
        Requirement originalRequirement = getRequirementOrThrow(id);

        originalRequirement.setRequirementText(requirement.getRequirementText());
        originalRequirement.setShowOrder(requirement.getShowOrder());
        originalRequirement.setPriority(requirement.getPriority());

        return originalRequirement;
    }

    @Override
    public void delete(String id) {
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
    private Requirement getRequirementOrThrow(@NotNull String id)
            throws EntityNotFoundException {
        return requirementRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No SRequirement exists with Id " + id));
    }
}
