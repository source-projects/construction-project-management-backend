package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImp(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Set<Project> getAllProjects() {
        Set<Project> projects = new HashSet<Project>();
        projectRepository.findAll().iterator().forEachRemaining(projects::add);
        return projects;
    }
}
