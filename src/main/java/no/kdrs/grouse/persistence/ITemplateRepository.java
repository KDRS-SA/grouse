package no.kdrs.grouse.persistence;


import no.kdrs.grouse.model.Template;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITemplateRepository
        extends PagingAndSortingRepository<Template, UUID> {
    List<Template> findByOwnedBy(String ownedBy);
}
