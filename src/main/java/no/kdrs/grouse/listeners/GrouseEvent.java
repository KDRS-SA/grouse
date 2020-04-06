package no.kdrs.grouse.listeners;

import no.kdrs.grouse.model.Project;
import org.springframework.context.ApplicationEvent;

import javax.validation.constraints.NotNull;

public class GrouseEvent
        extends ApplicationEvent {

    private Project project;

    public GrouseEvent(Object source, @NotNull Project project) {
        super(source);
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
