package no.kdrs.grouse.persistence.user;

import no.kdrs.grouse.model.GrouseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<GrouseUser, String> {
    GrouseUser findByUsername(String username);
}
