package no.kdrs.grouse.service.interfaces;


import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.utils.PatchObjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by tsodring on 28/03/18.
 */
public interface IGrouseUserService {

    Page<GrouseUser> findAll(Pageable pageable);

    GrouseUser findById(String id);

    GrouseUser save(GrouseUser requirement);

    GrouseUser update(String username, PatchObjects patchObjects);

    void delete(String id);
}
