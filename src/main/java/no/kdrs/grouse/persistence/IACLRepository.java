package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.AccessControl;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IACLRepository
        extends PagingAndSortingRepository<AccessControl, UUID> {
    List<AccessControl> findByGrouseUserAndObjectType(
            String grouseUser, String objectType);

    Optional<AccessControl> findFirstByOwnedByAndGrouseObject(
            String ownedBy, UUID grouseObject);

    Optional<AccessControl> findFirstByGrouseUserAndGrouseObject(
            String ownedBy, UUID grouseObject);

    List<AccessControl> findByGrouseObject(UUID grouseObject);
}
