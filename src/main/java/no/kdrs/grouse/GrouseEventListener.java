package no.kdrs.grouse;

import no.kdrs.grouse.listeners.GrouseCreationEvent;
import no.kdrs.grouse.model.AccessControl;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.service.ACLService;
import no.kdrs.grouse.utils.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static no.kdrs.grouse.utils.Constants.PROJECT;

@Component
@Transactional
public class GrouseEventListener {

    private static final Logger logger =
            LoggerFactory.getLogger(GrouseEventListener.class);

    @Autowired
    private ACLService aclService;


    @EventListener
    public void handleCreationEvent(GrouseCreationEvent event) {
        Project project = event.getProject();
        logger.info("Creating a object of type Project");
        AccessControl accessControl = new AccessControl();
        accessControl.setObjectType(PROJECT);
        accessControl.setGrouseUser(getUser());
        accessControl.setGrouseObject(project.getProjectId());
        aclService.createACLEntryOnCreate(project.getProjectId(), accessControl);

    }

    public String getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            return context.getAuthentication().getName();
        }
        throw new InternalException("Unable to find security context in " +
                "getUser()");
    }
}
