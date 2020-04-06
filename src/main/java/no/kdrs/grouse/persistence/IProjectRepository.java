package no.kdrs.grouse.persistence;


import no.kdrs.grouse.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProjectRepository
        extends PagingAndSortingRepository<Project, UUID> {
    Page<Project> findByOwnedBy(String ownedBy, Pageable pageable);

    Page<Project> findByProjectIdIn(List<UUID> projectIdList, Pageable pageable);

    Iterable<Project> findByOwnedBy(String ownedBy);
}
