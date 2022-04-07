package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.responses.PageResponse;
import com.greyhammer.erpservice.services.ProjectService;
import com.greyhammer.erpservice.utils.UserSessionUtil;
import com.greyhammer.erpservice.views.ProjectView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Slf4j
@RestController
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @JsonView(ProjectView.MinimalView.class)
    @RequestMapping("/api/projects")
    public ResponseEntity<PageResponse<Project>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String desc
    ) {
        Sort sort = desc != null && desc.equals("true") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Set<Project> results = projectService.getAllProjects(pageable);
        PageResponse response = new PageResponse<Project>();
        response.setResults(results);
        response.setCount(results.size());
        response.setPage(page);
        response.setTotal(projectService.getTotalProjectCount());

        return ResponseEntity.ok(response);
    }


    @RequestMapping("/api/projects/{id}")
    @JsonView(ProjectView.FullView.class)
    public ResponseEntity<Object> get(@PathVariable Long id) {
        try {
            Project project = projectService.getProjectById(id);
            return ResponseEntity.ok(project);
        } catch (ProjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping
    @JsonView(ProjectView.FullView.class)
    @RequestMapping(value = "/api/projects", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody CreateProjectCommand command) {
        try {
            if(!UserSessionUtil.getCurrentUserRoles().contains("admin")) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "You do not have the permission to create a project.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            Project project = projectService.handleCreateCommand(command);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(project.getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(project);

        } catch (CustomerNotFoundException cnfex) {
            log.error(cnfex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Customer not found.");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
