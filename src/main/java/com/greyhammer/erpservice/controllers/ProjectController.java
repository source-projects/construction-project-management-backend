package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.exceptions.NoPermissionException;
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
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String desc
    ) {
        Sort.Direction dir = desc != null && desc.equals("true") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = sortBy == null
            ? PageRequest.of(page, size)
            : PageRequest.of(page, size, Sort.by(dir, sortBy));

        Set<Project> results = projectService.getAll(pageable);
        PageResponse<Project> response = new PageResponse<>();
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
            Project project = projectService.get(id);
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
    @RequestMapping(value = "/api/projects/{id}/accounting/{action}", method = RequestMethod.POST)
    public ResponseEntity<Object> accounting(@PathVariable Long id, @PathVariable String action) {
        try {
            if(!UserSessionUtil.getCurrentUserRoles().contains("accounting")) {
                throw new NoPermissionException();
            }

            if (action.equals("approve")) {
                projectService.approveAsAccounting(id);
                return ResponseEntity.ok().build();
            } else if (action.equals("reject")) {
                projectService.rejectAsAccounting(id);
                return ResponseEntity.ok().build();
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Invalid action.");
                return ResponseEntity.badRequest().body(response);
            }

        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "You do not have the permission to create a project.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (ProjectNotFoundException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Project not found.");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping
    @JsonView(ProjectView.FullView.class)
    @RequestMapping(value = "/api/projects/{id}/stakeholder/{action}", method = RequestMethod.POST)
    public ResponseEntity<Object> stakeholder(@PathVariable Long id, @PathVariable String action) {
        try {
            if(!UserSessionUtil.getCurrentUserRoles().contains("stakeholder")) {
                throw new NoPermissionException();
            }

            if (action.equals("approve")) {
                projectService.approveAsStakeholder(id);
                return ResponseEntity.ok().build();
            } else if (action.equals("reject")) {
                projectService.rejectAsStakeholder(id);
                return ResponseEntity.ok().build();
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Invalid action.");
                return ResponseEntity.badRequest().body(response);
            }

        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "You do not have the permission to create a project.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (ProjectNotFoundException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Project not found.");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping
    @JsonView(ProjectView.FullView.class)
    @RequestMapping(value = "/api/projects/{id}/client/{action}", method = RequestMethod.POST)
    public ResponseEntity<Object> client(@PathVariable Long id, @PathVariable String action) {
        try {
            if(!UserSessionUtil.getCurrentUserRoles().contains("accounting")) {
                throw new NoPermissionException();
            }

            if (action.equals("approve")) {
                projectService.approveAsClient(id);
                return ResponseEntity.ok().build();
            } else if (action.equals("reject")) {
                projectService.rejectAsClient(id);
                return ResponseEntity.ok().build();
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Invalid action.");
                return ResponseEntity.badRequest().body(response);
            }

        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "You do not have the permission to create a project.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (ProjectNotFoundException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Project not found.");
            return ResponseEntity.badRequest().body(response);
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
                throw new NoPermissionException();
            }

            Project project = projectService.handleCreateCommand(command);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(project.getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(project);

        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "You do not have the permission to create a project.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (CustomerNotFoundException ex) {
            log.error(ex.toString());
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
