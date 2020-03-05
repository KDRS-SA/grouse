package no.kdrs.grouse.persistence;

import no.kdrs.grouse.model.GrouseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tsodring 28/03/2018
 * <p>
 * Repository for GrouseUser. Empty as we are just using from PagingAndSortingRepository.
 */
@Repository
public interface IGrouseUserRepository
        extends PagingAndSortingRepository<GrouseUser, String> {
    Page<GrouseUser> findAll(Pageable pageable);
}
