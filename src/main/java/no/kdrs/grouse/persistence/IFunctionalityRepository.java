package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.Functionality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFunctionalityRepository
        extends CrudRepository<Functionality, Long> {

    @Override
    List<Functionality> findAll();

    List<Functionality> findAllByOrderById();

    List<Functionality> findByShowMeAndReferenceParentFunctionality(
            Boolean menuItem, Functionality parent);
    Functionality findByFunctionalityNumber(String functionalityNumber);
}
