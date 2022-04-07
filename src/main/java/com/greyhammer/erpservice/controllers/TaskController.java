package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.exceptions.TaskInvalidAssignException;
import com.greyhammer.erpservice.exceptions.TaskNotFoundException;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.services.TaskService;
import com.greyhammer.erpservice.utils.JwtAuthentication;
import com.greyhammer.erpservice.views.TaskView;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @JsonView(TaskView.ListView.class)
    @RequestMapping("/api/tasks/unassigned")
    public ResponseEntity<?> getUnassignedTasks() {
        try {
            Set<Task> task = taskService.getUnassignedTaskByRoles(getCurrentUserRoles());
            return ResponseEntity.ok(task);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @JsonView(TaskView.ListView.class)
    @RequestMapping("/api/tasks/pending")
    public ResponseEntity<?> getPendingTasks() {
        try {
            Set<Task> task = taskService.getAssignedPendingTask(getCurrentUsername());
            return ResponseEntity.ok(task);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @JsonView(TaskView.ListView.class)
    @RequestMapping("/api/tasks/completed")
    public ResponseEntity<?> getCompletedTasks() {
        try {
            Set<Task> task = taskService.getAssignedCompletedTask(getCurrentUsername());
            return ResponseEntity.ok(task);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @JsonView(TaskView.ListView.class)
    @RequestMapping(value = "/api/tasks/{id}/assign", method = RequestMethod.POST)
    public ResponseEntity<?> assignTask(@PathVariable Long id) {
        try {
            taskService.assignTask(id, getCurrentUserRoles(), getCurrentUsername());
            return ResponseEntity.accepted().build();
        } catch (TaskInvalidAssignException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cannot assign task.");
            return ResponseEntity.badRequest().body(response);
        } catch (TaskNotFoundException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Task not found.");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    private String getCurrentUsername() {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private Set<String> getCurrentUserRoles() {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

        JSONArray roleClaims = (JSONArray) authentication.getJwtClaimsSet().getClaim("cognito:groups");
        Set<String> roles = new HashSet<>();
        for (Object role: roleClaims.toArray()) {
            roles.add(role.toString());
        }
        return roles;
    }
}
