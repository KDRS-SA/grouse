package no.kdrs.grouse.service;

import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.persistence.IGrouseUserRepository;
import no.kdrs.grouse.service.interfaces.IGrouseUserService;
import no.kdrs.grouse.utils.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by tsodring on 28/03/18.
 */
@Service
@Transactional
public class GrouseUserService
        implements IGrouseUserService {

    private IGrouseUserRepository userRepository;
    private PasswordEncoder encoder;

    public GrouseUserService(IGrouseUserRepository userRepository,
                             PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @SuppressWarnings("unchecked")
    public List<GrouseUser> findAll() {
        return (ArrayList) userRepository.findAll();
    }

    @Override
    public GrouseUser findById(String id) {
        return getGrouseUserOrThrow(id);
    }

    @Override
    public GrouseUser save(GrouseUser incomingUser) {
        checkGrouseUserExistThenThrow(incomingUser.getUsername());
        GrouseUser user = new GrouseUser();
        user.setPassword(encoder.encode(incomingUser.getPassword()));
        user.setUsername(incomingUser.getUsername());

        return userRepository.save(user);
    }

    @Override
    public GrouseUser update(String id, GrouseUser user)
            throws EntityNotFoundException {
        GrouseUser originalGrouseUser = getGrouseUserOrThrow(id);

        //originalGrouseUser.setPassword(encoder.encode(user.getPassword()));

        return originalGrouseUser;
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid GrouseUser back. If there is no valid
     * GrouseUser, a EntityNotFoundException exception is thrown
     *
     * @param id The id of the user object to retrieve
     * @return the user object
     */
    private GrouseUser getGrouseUserOrThrow(@NotNull String id)
            throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No GrouseUser exists with Id " + id));
    }

    /**
     * Internal helper method. Check if GrouseUser exists
     *
     * @param id The systemId of the user object to check existence
     */
    private void checkGrouseUserExistThenThrow(@NotNull String id)
            throws UserAlreadyExistsException {
        Optional<GrouseUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            throw new UserAlreadyExistsException(
                    "No GrouseUser exists with Id " + id);
        }
    }
}
