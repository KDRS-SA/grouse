package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.Functionality;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by tsodring on 9/25/17.
 */
public interface IFunctionalityService {
    List<Functionality> findAll();
    Functionality findById(Long id);
    Functionality findByFunctionalityNumber(String functionalityNumber);
    Functionality save(Functionality functionality);
    Functionality update(Long id, Functionality functionality)
            throws EntityNotFoundException;
    void delete(Long id);

    List<Functionality> findByShowMeAndReferenceParentFunctionality(
            Boolean menuItem, String parent);
}
