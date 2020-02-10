package no.kdrs.grouse.service;

import no.kdrs.grouse.model.TemplateFunctionality;
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

    public List<TemplateFunctionality> findAll() {
        List<TemplateFunctionality> templateFunctionality = functionalityRepository.findAll();
        return templateFunctionality;
    }

    @Override
    public List<TemplateFunctionality> findByShowMeAndReferenceParentFunctionality(
            Boolean menuItem, String parent) {
        TemplateFunctionality templateFunctionality = new TemplateFunctionality.
                FunctionalityBuilder().functionalityNumber(parent).build();
        return functionalityRepository.
                findByShowMeAndReferenceParentTemplateFunctionality(
                        menuItem, templateFunctionality);
    }

    @Override
    public TemplateFunctionality findById(Long id) {
        return getFunctionalityOrThrow(id);
    }

    @Override
    public TemplateFunctionality findByFunctionalityNumber(String functionalityNumber) {
        TemplateFunctionality templateFunctionality =
                functionalityRepository.findByFunctionalityNumber
                        (functionalityNumber);
        if (templateFunctionality == null) {
            throw new EntityNotFoundException("No TemplateFunctionality " +
                    " exists with Id " + functionalityNumber);
        }
        return templateFunctionality;

    }

    @Override
    public TemplateFunctionality save(TemplateFunctionality TemplateFunctionality) {
        return functionalityRepository.save(TemplateFunctionality);
    }

    @Override
    public TemplateFunctionality update(Long id, TemplateFunctionality templateFunctionality)
            throws EntityNotFoundException {
        TemplateFunctionality originalTemplateFunctionality = getFunctionalityOrThrow(id);

        originalTemplateFunctionality.setDescription(templateFunctionality.getDescription());
        originalTemplateFunctionality.setConsequence(templateFunctionality.getConsequence());
        originalTemplateFunctionality.setExplanation(templateFunctionality.getExplanation());

        return originalTemplateFunctionality;
    }
    
    @Override
    public void delete(Long id) {
        functionalityRepository.deleteById(id);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid TemplateFunctionality back. If there is no valid
     * TemplateFunctionality, a EntityNotFoundException exception is thrown
     *
     * @param id The systemId of the functionality object to retrieve
     * @return the functionality object
     */
    private TemplateFunctionality getFunctionalityOrThrow(@NotNull Long id)
            throws EntityNotFoundException {
        TemplateFunctionality templateFunctionality =
                functionalityRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "No TemplateFunctionality exists with Id " + id));
        return templateFunctionality;
    }

}
