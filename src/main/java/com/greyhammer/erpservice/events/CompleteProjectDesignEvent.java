package com.greyhammer.erpservice.events;

import com.greyhammer.erpservice.models.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CompleteProjectDesignEvent extends ApplicationEvent {
    private Project project;
    
    public CompleteProjectDesignEvent(Object source) {
        super(source);
    }
}
