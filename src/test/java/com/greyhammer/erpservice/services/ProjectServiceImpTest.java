package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.converters.CreateProjectCommandToProjectConverter;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceImpTest {

    ProjectServiceImp projectService;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    CreateProjectCommandToProjectConverter createProjectCommandToProjectConverter;

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @Mock
    CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        projectService = new ProjectServiceImp(projectRepository, createProjectCommandToProjectConverter, customerService, applicationEventPublisher);
    }

    @Test
    void getAllProjects() {
        when(projectRepository.findAll(PageRequest.of(0, 5))).thenReturn(Page.empty());
        assertEquals(projectService.getAll(PageRequest.of(0,5)).size(), 0);
        verify(projectRepository, times(1)).findAll(PageRequest.of(0, 5));
    }

    @Test
    void getProject() {
        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        assertDoesNotThrow(() -> projectService.get(1L));
        assertThrows(ProjectNotFoundException.class, () -> projectService.get(2L));
    }
}