package no.kdrs.grouse.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static no.kdrs.grouse.model.user.AuthorityName.*;

@Component("roleValidator")
public class RoleValidator {

    public boolean isUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority()
                        .equals(ROLE_USER.toString()));
    }

    public boolean isTemplate() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority()
                        .equals(ROLE_TEMPLATE.toString()));
    }

    public boolean isAdmin() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority()
                        .equals(ROLE_ADMIN.toString()));
    }
}
