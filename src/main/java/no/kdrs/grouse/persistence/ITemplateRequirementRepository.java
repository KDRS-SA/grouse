package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.TemplateRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemplateRequirementRepository
        extends PagingAndSortingRepository<TemplateRequirement, Long> {
    Page findByFunctionalityFunctionalityNumber(String functionalityNumber,
                                                Pageable pageable);
}
