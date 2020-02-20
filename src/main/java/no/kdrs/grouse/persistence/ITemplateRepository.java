package no.kdrs.grouse.persistence;


import no.kdrs.grouse.model.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITemplateRepository
        extends CrudRepository<Template, Long> {
    List<Template> findByOwnedBy(String ownedBy);
}
