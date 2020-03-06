package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.GrouseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

/**
 * Created by tsodring on 28/03/18.
 */
public interface IGrouseUserService {

    Page<GrouseUser> findAll(Pageable pageable);

    GrouseUser findById(String id);

    GrouseUser save(GrouseUser requirement);

    GrouseUser update(String requirementId, GrouseUser requirement)
            throws EntityNotFoundException;

    void delete(String id);
}
