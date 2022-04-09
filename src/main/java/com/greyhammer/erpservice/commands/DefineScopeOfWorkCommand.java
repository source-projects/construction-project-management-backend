package com.greyhammer.erpservice.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DefineScopeOfWorkCommand {
    public enum CommandType {
        DELETE, CREATE, UPDATE
    }

    public interface Command {
        Long getId();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ScopeCommand implements Command {
        private Long projectId;
        private CommandType type;
        private Long id;
        private String name;
        private String unit;
        private Double qty;
        private Set<TaskCommand> tasks;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TaskCommand implements Command {
        private CommandType type;
        private Long id;
        private String name;
        private String unit;
        private Double qty;
        private Set<MaterialCommand> materials;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MaterialCommand implements Command {
        private CommandType type;
        private Long id;
        private String name;
        private String unit;
        private Double qty;
        private Double contingency;
    }

    private Set<ScopeCommand> scopes;
}
