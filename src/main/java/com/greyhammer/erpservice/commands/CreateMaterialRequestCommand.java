package com.greyhammer.erpservice.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CreateMaterialRequestCommand {
    private Long projectId;
    private Long taskId;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateMaterialRequestItemCommand {
        private Long materialId;
        private Double qty;
    }

    private Set<CreateMaterialRequestItemCommand> items;
}
