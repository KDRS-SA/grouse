package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.ProjectFunctionality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectFunctionalityRepository
        extends PagingAndSortingRepository<ProjectFunctionality, Long> {

    Page<ProjectFunctionality> findByReferenceProjectAndTypeAndShowMe(
            Project project, String type, Boolean showMe, Pageable pageable);

    Page<ProjectFunctionality> findByReferenceParentFunctionality(
            Pageable pageable, ProjectFunctionality projectFunctionality);
}
