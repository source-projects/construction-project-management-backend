package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Project;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface ProjectService {
    Set<Project> getAllProjects(Pageable pageable);
    Project getProjectById(Long id) throws ProjectNotFoundException;
    Project handleCreateCommand(CreateProjectCommand command) throws CustomerNotFoundException;
}
