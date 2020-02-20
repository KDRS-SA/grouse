package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.TemplateRequirement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemplateRequirementRepository
        extends CrudRepository<TemplateRequirement, Long> {
}
