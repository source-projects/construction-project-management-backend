package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.models.Project;

import java.util.Optional;
import java.util.Set;

public interface ProjectService {
    Set<Project> getAllProjects();
    Optional<Project> getProjectById(Long id);
}
