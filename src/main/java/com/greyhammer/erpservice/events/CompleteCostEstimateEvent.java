package com.greyhammer.erpservice.events;

import com.greyhammer.erpservice.models.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class CompleteCostEstimateEvent extends ApplicationEvent {
    private Project project;

    public CompleteCostEstimateEvent(Object source) {
        super(source);
    }
}
