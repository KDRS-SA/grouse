package no.kdrs.grouse.spring;

import no.kdrs.grouse.model.user.GrouseUserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.ManagedBean;
import java.time.OffsetDateTime;
import java.util.Optional;

@ManagedBean
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class SpringSecurityAuditorAware
        implements AuditorAware<String> {

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof String) {
            return Optional.of((String) authentication.getPrincipal());
        }
        return Optional.of(((GrouseUserPrincipal)
                authentication.getPrincipal()).getUsername());
    }
}