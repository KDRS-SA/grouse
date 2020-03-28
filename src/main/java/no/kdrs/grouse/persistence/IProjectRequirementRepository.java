package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.ProjectFunctionality;
import no.kdrs.grouse.model.ProjectRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRequirementRepository
        extends PagingAndSortingRepository<ProjectRequirement, Long> {
    Page<ProjectRequirement> findByReferenceFunctionality(
            ProjectFunctionality projectFunctionality, Pageable pageable);
}
