package no.kdrs.grouse.persistence;


import no.kdrs.grouse.model.Template;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITemplateRepository
        extends PagingAndSortingRepository<Template, Long> {
    List<Template> findByOwnedBy(String ownedBy);
}
