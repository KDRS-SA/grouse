package no.kdrs.grouse.persistence;


import no.kdrs.grouse.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRepository
        extends PagingAndSortingRepository<Project, Long> {
    Page<Project> findByOwnedBy(String ownedBy, Pageable pageable);

    Iterable<Project> findByOwnedBy(String ownedBy);
}
