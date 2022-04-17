package com.greyhammer.erpservice.events;

import com.greyhammer.erpservice.models.MaterialRequest;
import com.greyhammer.erpservice.models.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CreateMaterialRequestEvent extends ApplicationEvent {
    private MaterialRequest request;

    public CreateMaterialRequestEvent(Object source) {
        super(source);
    }
}
