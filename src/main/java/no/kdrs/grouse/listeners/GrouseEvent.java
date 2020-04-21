package no.kdrs.grouse.listeners;

import no.kdrs.grouse.model.Project;
import no.kdrs.grouse.model.Template;
import org.springframework.context.ApplicationEvent;

import javax.validation.constraints.NotNull;

public class GrouseEvent
        extends ApplicationEvent {

    private Project project;
    private Template template;

    public GrouseEvent(Object source, @NotNull Project project) {
        super(source);
        this.project = project;
    }

    public GrouseEvent(Object source, @NotNull Template template) {
        super(source);
        this.template = template;
    }

    public Template getTemplate() {
        return template;
    }

    public Project getProject() {
        return project;
    }
}
