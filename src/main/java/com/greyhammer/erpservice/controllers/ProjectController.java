package com.greyhammer.erpservice.controllers;

import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@RestController
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping("/projects")
    public Set<Project> getAll() {
        return projectService.getAllProjects();
    }


    @RequestMapping("/projects/{id}")
    public Project get(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isPresent())
            return project.get();
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project does not exist.");
        }
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public Project create(@ModelAttribute CreateProjectCommand command) {

        return null;
    }
}
