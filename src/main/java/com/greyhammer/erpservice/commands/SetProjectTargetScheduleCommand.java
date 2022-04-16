package com.greyhammer.erpservice.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class SetProjectTargetScheduleCommand {
    public enum CommandType {
        CREATE, UPDATE, DELETE
    }

    @Getter
    @Setter
    public static class ScheduleCommand {
        private Long id;
        private CommandType type;
        private Long projectId;
        private Long taskId;
        private Date start;
        private Date end;
        private Set<ScheduleDateCommand> dates;
    }

    @Getter
    @Setter
    public static class ScheduleDateCommand {
        private Date date;
        private Long materialId;
        private Float target;
    }

    private Long projectId;
    private Set<ScheduleCommand> commands;
}
