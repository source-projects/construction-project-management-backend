package com.greyhammer.erpservice.events;

import com.greyhammer.erpservice.models.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class TaskEvent extends ApplicationEvent {
    private Project project;

    public TaskEvent(Object source) {
        super(source);
    }
}
