package com.greyhammer.erpservice.commands;

import com.greyhammer.erpservice.models.ProjectDesignStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetProjectDesignStatusCommand {
    Long projectId;
    ProjectDesignStatus status;
}
