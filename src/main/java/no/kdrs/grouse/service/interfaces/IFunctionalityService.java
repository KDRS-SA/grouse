package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.TemplateFunctionality;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
public interface IFunctionalityService {
    List<TemplateFunctionality> findAll();

    TemplateFunctionality findById(Long id);

    TemplateFunctionality findByFunctionalityNumber(String functionalityNumber);

    TemplateFunctionality save(TemplateFunctionality templateFunctionality);

    TemplateFunctionality update(Long id, TemplateFunctionality templateFunctionality)
            throws EntityNotFoundException;

    void delete(Long id);

    List<TemplateFunctionality> findByShowMeAndReferenceParentFunctionality(
            Boolean menuItem, String parent);
}
