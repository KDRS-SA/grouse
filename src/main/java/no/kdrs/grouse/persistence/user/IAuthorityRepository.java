package no.kdrs.grouse.persistence.user;

import no.kdrs.grouse.model.user.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tsodring 28/03/2018
 */

@Repository
public interface IAuthorityRepository
        extends CrudRepository<Authority, String> {
}
