package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.AccessControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IACLRepository
        extends PagingAndSortingRepository<AccessControl, UUID> {
    Page<AccessControl> findByOwnedBy(String ownedBy, Pageable pageable);
}
