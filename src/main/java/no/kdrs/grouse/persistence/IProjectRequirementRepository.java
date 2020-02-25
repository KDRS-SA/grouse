package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.ProjectRequirement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRequirementRepository
        extends PagingAndSortingRepository<ProjectRequirement, Long> {
}
