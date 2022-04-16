package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.ProjectTargetSchedule;
import com.greyhammer.erpservice.repositories.ProjectTargetScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectTargetScheduleServiceImp implements ProjectTargetScheduleService {
    private final ProjectService projectService;
    private final ProjectTargetScheduleRepository projectScheduleRepository;

    public ProjectTargetScheduleServiceImp(ProjectService projectService, ProjectTargetScheduleRepository projectScheduleRepository) {
        this.projectService = projectService;
        this.projectScheduleRepository = projectScheduleRepository;
    }

    @Override
    public Set<ProjectTargetSchedule> getAllSchedulesByProjectId(Long projectId) throws ProjectNotFoundException {
        projectService.get(projectId);
        return projectScheduleRepository.findAllByProjectId(projectId);
    }
}
