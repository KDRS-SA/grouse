package no.kdrs.grouse.service;

import no.kdrs.grouse.utils.exception.InternalException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GrouseService {

    public String getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            return context.getAuthentication().getName();
        }
        throw new InternalException("Unable to find security context in " +
                "getUser()");
    }
}
