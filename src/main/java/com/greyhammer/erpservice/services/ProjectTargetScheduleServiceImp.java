package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.SetProjectTargetScheduleCommand;
import com.greyhammer.erpservice.converters.SetProjectTargetScheduleCommandToProjectTargetScheduleConverter;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.ProjectTargetSchedule;
import com.greyhammer.erpservice.models.ProjectTargetScheduleDate;
import com.greyhammer.erpservice.repositories.ProjectTargetScheduleDateRepository;
import com.greyhammer.erpservice.repositories.ProjectTargetScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectTargetScheduleServiceImp implements ProjectTargetScheduleService {
    private final ProjectService projectService;
    private final ProjectTargetScheduleRepository projectTargetScheduleRepository;
    private final ProjectTargetScheduleDateRepository projectTargetScheduleDateRepository;
    private final SetProjectTargetScheduleCommandToProjectTargetScheduleConverter
            setProjectTargetScheduleCommandToProjectTargetScheduleConverter;

    public ProjectTargetScheduleServiceImp(
            ProjectService projectService, ProjectTargetScheduleRepository projectTargetScheduleRepository,
            ProjectTargetScheduleDateRepository projectTargetScheduleDateRepository,
            SetProjectTargetScheduleCommandToProjectTargetScheduleConverter setProjectTargetScheduleCommandToProjectTargetScheduleConverter) {
        this.projectService = projectService;
        this.projectTargetScheduleRepository = projectTargetScheduleRepository;
        this.projectTargetScheduleDateRepository = projectTargetScheduleDateRepository;
        this.setProjectTargetScheduleCommandToProjectTargetScheduleConverter = setProjectTargetScheduleCommandToProjectTargetScheduleConverter;
    }

    @Override
    public Set<ProjectTargetSchedule> getAllSchedulesByProjectId(Long projectId) throws ProjectNotFoundException {
        projectService.get(projectId);
        return projectTargetScheduleRepository.findAllByProjectId(projectId);
    }

    @Override
    @Transactional
    public Set<ProjectTargetSchedule> handleSetProjectTargetScheduleCommand(
            Long projectId, SetProjectTargetScheduleCommand source) throws ProjectNotFoundException {
        projectService.get(projectId);

        Set<ProjectTargetSchedule> schedules = new HashSet<>();
        for (SetProjectTargetScheduleCommand.ScheduleCommand command : source.getCommands()) {
            command.setProjectId(source.getProjectId());

            if (command.getType() == SetProjectTargetScheduleCommand.CommandType.CREATE) {
                ProjectTargetSchedule schedule = handleCreateCommand(command);

                if (schedule != null) {
                    schedules.add(schedule);
                }
            } else if (command.getType() == SetProjectTargetScheduleCommand.CommandType.UPDATE) {
                ProjectTargetSchedule schedule = handleUpdateCommand(command);

                if (schedule != null) {
                    schedules.add(schedule);
                }
            } else if (command.getType() == SetProjectTargetScheduleCommand.CommandType.DELETE) {
                handleDeleteCommand(command);
            }
        }

        return schedules;
    }

    private ProjectTargetSchedule handleCreateCommand(SetProjectTargetScheduleCommand.ScheduleCommand command) {

        ProjectTargetSchedule newSchedule =
                setProjectTargetScheduleCommandToProjectTargetScheduleConverter.convert(command);

        if (newSchedule != null) {
            projectTargetScheduleRepository.save(newSchedule);
            projectTargetScheduleDateRepository.saveAll(newSchedule.getDates());

            return newSchedule;
        }

        return null;
    }

    private ProjectTargetSchedule handleUpdateCommand(SetProjectTargetScheduleCommand.ScheduleCommand command) {
        Optional<ProjectTargetSchedule> optionalSchedule = projectTargetScheduleRepository.findById(
                command.getId());

        if (optionalSchedule.isPresent()) {
            ProjectTargetSchedule schedule = optionalSchedule.get();
            Set<ProjectTargetScheduleDate> existingDates = projectTargetScheduleDateRepository
                    .findAllByScheduleId(schedule.getId());
            projectTargetScheduleDateRepository.deleteAll(existingDates);

            ProjectTargetSchedule newSchedule =
                    setProjectTargetScheduleCommandToProjectTargetScheduleConverter.convert(command);
            if (newSchedule != null) {
                schedule.setStart(newSchedule.getStart());
                schedule.setEnd(newSchedule.getEnd());
                schedule.setTask(newSchedule.getTask());

                Set<ProjectTargetScheduleDate> dates = new HashSet<>();
                for (ProjectTargetScheduleDate date : newSchedule.getDates()) {
                    date.setSchedule(schedule);
                    projectTargetScheduleDateRepository.save(date);
                }

                schedule.setDates(dates);
                projectTargetScheduleRepository.save(schedule);
            }
            return schedule;
        }

        return null;
    }

    private void handleDeleteCommand(SetProjectTargetScheduleCommand.ScheduleCommand command) {
        Optional<ProjectTargetSchedule> optionalSchedule = projectTargetScheduleRepository.findById(
                command.getId());

        optionalSchedule.ifPresent(projectTargetScheduleRepository::delete);
    }
}
