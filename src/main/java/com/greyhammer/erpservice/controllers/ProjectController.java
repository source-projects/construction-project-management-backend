package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.services.ProjectService;
import com.greyhammer.erpservice.views.ProjectView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @JsonView(ProjectView.MinimalView.class)
    @RequestMapping("/projects")
    public ResponseEntity<Set<Project>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String desc
    ) {
        Sort sort = desc == "true" ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }


    @RequestMapping("/projects/{id}")
    @JsonView(ProjectView.FullView.class)
    public ResponseEntity<Project> get(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isPresent())
            return ResponseEntity.ok(project.get());
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @JsonView(ProjectView.FullView.class)
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody CreateProjectCommand command) {
        try {
            Project project = projectService.handleCreateCommand(command);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(project.getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(project);

        } catch (CustomerNotFoundException cnfex) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("message", "Customer not found.");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception ex) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
