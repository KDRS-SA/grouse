package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITemplateFunctionalityRepository
        extends PagingAndSortingRepository<TemplateFunctionality, Long> {

    Page<TemplateFunctionality> findByReferenceTemplate(
            Template template, Pageable pageable);

    Page<TemplateFunctionality> findByReferenceParentFunctionality(
            Pageable pageable, TemplateFunctionality templateFunctionality);

    List<TemplateFunctionality> findByReferenceTemplate(Template template);

    List<TemplateFunctionality>
    findByFunctionalityNumber(String functionalityNumber);

    List<TemplateFunctionality> findByFunctionalityNumberAndReferenceTemplate(
            String functionalityNumber, Template template);
}
