package no.kdrs.grouse.spring;

import no.kdrs.grouse.model.user.GrouseUserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.ManagedBean;
import java.util.Optional;

import static no.kdrs.grouse.utils.Constants.SYSTEM_USER;

@ManagedBean
public class SpringSecurityAuditorAware
        implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.of(SYSTEM_USER);
        }
        if (authentication.getPrincipal() instanceof String) {
            return Optional.of((String) authentication.getPrincipal());
        }
        return Optional.of(((GrouseUserPrincipal)
                authentication.getPrincipal()).getUsername());
    }
}