package no.kdrs.grouse.controller;

import no.kdrs.grouse.utils.RoleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import static no.kdrs.grouse.utils.Constants.NO_ACCESS_OTHER_USER;

public class GrouseController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RoleValidator role;

    protected void checkAccess(String ownedBy) {
        String loggedInUser = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        if (!role.isAdmin()) {
            if (!loggedInUser.equals(ownedBy)) {
                logger.error(NO_ACCESS_OTHER_USER);
                throw new AccessDeniedException(NO_ACCESS_OTHER_USER);
            }
        }
    }
}
