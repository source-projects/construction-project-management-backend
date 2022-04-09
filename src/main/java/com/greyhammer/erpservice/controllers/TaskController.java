package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.TaskInvalidAssignException;
import com.greyhammer.erpservice.exceptions.TaskNotFoundException;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.services.TaskService;
import com.greyhammer.erpservice.views.TaskView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
            Set<Task> tasks = taskService.getUnassignedTask();
            return ResponseEntity.ok(tasks);
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
            Set<Task> task = taskService.getAssignedPendingTask();
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
            Set<Task> task = taskService.getAssignedCompletedTask();
            return ResponseEntity.ok(task);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @RequestMapping(value = "/api/tasks/{id}/assign", method = RequestMethod.POST)
    public ResponseEntity<?> assignTask(@PathVariable Long id) {
        try {
            taskService.assignTask(id);
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

    @RequestMapping(value = "/api/tasks/{id}/complete", method = RequestMethod.POST)
    public ResponseEntity<?> markAsComplete(@PathVariable Long id) {
        try {
            taskService.markAsComplete(id);
            return ResponseEntity.accepted().build();
        } catch (NoPermissionException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "No permission to update task.");
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
}
