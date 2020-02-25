package no.kdrs.grouse.persistence;


import no.kdrs.grouse.model.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository
        extends PagingAndSortingRepository<Project, Long> {
    List<Project> findByOwnedBy(String ownedBy);
}
