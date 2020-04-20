package no.kdrs.grouse.listeners;

import no.kdrs.grouse.model.AccessControl;
import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.Template;
import no.kdrs.grouse.service.ACLService;
import no.kdrs.grouse.utils.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static no.kdrs.grouse.utils.Constants.PROJECT;
import static no.kdrs.grouse.utils.Constants.TEMPLATE;

@Component
@Transactional
public class GrouseEventListener {

    @Autowired
    private ACLService aclService;

    @EventListener
    public void handleCreationEvent(GrouseCreationEvent event) {
        if (event.getProject() != null) {
            Project project = event.getProject();
            AccessControl accessControl = new AccessControl();
            accessControl.setObjectType(PROJECT);
            accessControl.setGrouseUser(getUser());
            accessControl.setGrouseObject(project.getProjectId());
            aclService.createACLEntryOnCreate(project.getProjectId(),
                    accessControl);
        } else if (event.getTemplate() != null) {
            Template template = event.getTemplate();
            AccessControl accessControl = new AccessControl();
            accessControl.setObjectType(TEMPLATE);
            accessControl.setGrouseUser(getUser());
            accessControl.setGrouseObject(template.getTemplateId());
            aclService.createACLEntryOnCreate(template.getTemplateId(), accessControl);
        }
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
