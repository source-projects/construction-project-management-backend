package com.greyhammer.erpservice.converters;

import com.greyhammer.erpservice.commands.SetProjectTargetScheduleCommand;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.ProjectTargetSchedule;
import com.greyhammer.erpservice.models.ProjectTargetScheduleDate;
import com.greyhammer.erpservice.services.ProjectService;
import com.greyhammer.erpservice.services.ScopeOfWorkService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SetProjectTargetScheduleCommandToProjectTargetScheduleConverter
        implements Converter<SetProjectTargetScheduleCommand.ScheduleCommand, ProjectTargetSchedule> {
    private final ProjectService projectService;
    private final ScopeOfWorkService scopeOfWorkService;

    public SetProjectTargetScheduleCommandToProjectTargetScheduleConverter(ProjectService projectService, ScopeOfWorkService scopeOfWorkService) {
        this.projectService = projectService;
        this.scopeOfWorkService = scopeOfWorkService;
    }

    @Override
    public ProjectTargetSchedule convert(SetProjectTargetScheduleCommand.ScheduleCommand source) {
        try {
            Project project = projectService.get(source.getProjectId());

            ProjectTargetSchedule schedule = new ProjectTargetSchedule();
            schedule.setProject(project);
            schedule.setTask(scopeOfWorkService.getTaskById(source.getTaskId()));
            schedule.setStart(source.getStart());
            schedule.setEnd(source.getEnd());

            Set<ProjectTargetScheduleDate> dates = new HashSet<>();

            for (SetProjectTargetScheduleCommand.ScheduleDateCommand dateCommand : source.getDates()) {
                ProjectTargetScheduleDate date = new ProjectTargetScheduleDate();
                date.setDate(dateCommand.getDate());
                date.setTarget(dateCommand.getTarget());
                date.setMaterial(scopeOfWorkService.getMaterialById(dateCommand.getMaterialId()));
                date.setSchedule(schedule);
                dates.add(date);
            }

            schedule.setDates(dates);
            return schedule;
        } catch (Exception ex) {
            return null;
        }
    }
}
