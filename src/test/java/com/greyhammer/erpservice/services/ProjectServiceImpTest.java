package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceImpTest {

    ProjectServiceImp projectService;

    @Mock
    ProjectRepository projectRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        projectService = new ProjectServiceImp(projectRepository);
    }

    @Test
    void getAllProjects() {
        Project project = new Project();
        Set<Project> projects = new HashSet<Project>();
        projects.add(project);

        when(projectRepository.findAll()).thenReturn(projects);

        assertEquals(projectService.getAllProjects().size(), 1);
        verify(projectRepository, times(1)).findAll();
    }
}