package com.greyhammer.erpservice.events;

import com.greyhammer.erpservice.models.MaterialRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteFinalMaterialRequestApprovalEvent extends TaskEvent {
    MaterialRequest request;

    public CompleteFinalMaterialRequestApprovalEvent(Object source) {
        super(source);
    }
}
