package com.greyhammer.erpservice.events;

import com.greyhammer.erpservice.models.MaterialRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteMaterialRequestApprovalEvent extends TaskEvent {
    MaterialRequest request;
    public CompleteMaterialRequestApprovalEvent(Object source) {
        super(source);
    }
}
