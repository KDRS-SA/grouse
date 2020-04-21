package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.model.TemplateFunctionality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemplateFunctionalityRepository
        extends PagingAndSortingRepository<TemplateFunctionality, Long> {

    Page<TemplateFunctionality> findByReferenceTemplate(
            Template template, Pageable pageable);

    Page<TemplateFunctionality> findByReferenceParentFunctionality(
            Pageable pageable, TemplateFunctionality templateFunctionality);
}
