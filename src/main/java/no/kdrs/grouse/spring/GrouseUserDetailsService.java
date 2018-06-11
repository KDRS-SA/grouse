package no.kdrs.grouse.spring;

import no.kdrs.grouse.model.GrouseUser;
import no.kdrs.grouse.model.user.GrouseUserPrincipal;
import no.kdrs.grouse.persistence.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GrouseUserDetailsService
        implements UserDetailsService {

    private UserRepository userRepository;

    public GrouseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {

        GrouseUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with this " +
                    "username: " + username);
        }
        return new GrouseUserPrincipal(user);
    }
}
