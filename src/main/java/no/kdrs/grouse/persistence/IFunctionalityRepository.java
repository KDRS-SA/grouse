package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.Functionality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFunctionalityRepository
        extends CrudRepository<Functionality, String> {

    @Override
    List<Functionality> findAll();

    List<Functionality> findAllByOrderByFunctionalityNumber();

    List<Functionality> findByShowMeAndReferenceParentFunctionality(
            Boolean menuItem, Functionality parent);
    Functionality findByFunctionalityNumber(String functionalityNumber);
}
