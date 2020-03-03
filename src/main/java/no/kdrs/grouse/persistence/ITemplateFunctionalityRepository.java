package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.TemplateFunctionality;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITemplateFunctionalityRepository
        extends PagingAndSortingRepository<TemplateFunctionality, Long> {

    @Override
    List<TemplateFunctionality> findAll();

    //List<TemplateFunctionality> findAllByOrderById();

    List<TemplateFunctionality>
    findByShowMeAndReferenceParentTemplateFunctionality(
            Boolean menuItem, TemplateFunctionality parent);

    TemplateFunctionality findByFunctionalityNumber(String functionalityNumber);
}