package no.kdrs.grouse.service;

import no.kdrs.grouse.model.Functionality;
import no.kdrs.grouse.persistence.IFunctionalityRepository;
import no.kdrs.grouse.service.interfaces.IFunctionalityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
@Service
@Transactional
public class FunctionalityService
        implements IFunctionalityService {

    private IFunctionalityRepository functionalityRepository;

    public FunctionalityService(IFunctionalityRepository
                                        functionalityRepository) {
        this.functionalityRepository = functionalityRepository;
    }

    public List<Functionality> findAll() {
        List<Functionality> functionality = functionalityRepository.findAll();
        return functionality;
    }

    @Override
    public List<Functionality> findByShowMeAndReferenceParentFunctionality(
            Boolean menuItem, String parent) {
        Functionality functionality = new Functionality.
                FunctionalityBuilder().functionalityNumber(parent).build();
        return functionalityRepository.
                findByShowMeAndReferenceParentFunctionality(
                        menuItem, functionality);
    }

    @Override
    public Functionality findById(Long id) {
        return getFunctionalityOrThrow(id);
    }

    @Override
    public Functionality findByFunctionalityNumber(String functionalityNumber) {
        Functionality functionality =
                functionalityRepository.findByFunctionalityNumber
                        (functionalityNumber);
        if (functionality == null) {
            throw new EntityNotFoundException("No Functionality " +
                    " exists with Id " + functionalityNumber);
        }
        return functionality;

    }

    @Override
    public Functionality save(Functionality Functionality) {
        return functionalityRepository.save(Functionality);
    }

    @Override
    public Functionality update(Long id, Functionality functionality)
            throws EntityNotFoundException {
        Functionality originalFunctionality = getFunctionalityOrThrow(id);

        originalFunctionality.setDescription(functionality.getDescription());
        originalFunctionality.setConsequence(functionality.getConsequence());
        originalFunctionality.setExplanation(functionality.getExplanation());

        return originalFunctionality;
    }
    
    @Override
    public void delete(Long id) {
        functionalityRepository.deleteById(id);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid Functionality back. If there is no valid
     * Functionality, a EntityNotFoundException exception is thrown
     *
     * @param id The systemId of the functionality object to retrieve
     * @return the functionality object
     */
    private Functionality getFunctionalityOrThrow(@NotNull Long id)
            throws EntityNotFoundException {
        Functionality functionality =
                functionalityRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "No Functionality exists with Id " + id));
        return functionality;
    }

}
