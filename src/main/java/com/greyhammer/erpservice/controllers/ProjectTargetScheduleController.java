package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.commands.SetProjectTargetScheduleCommand;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.ProjectTargetSchedule;
import com.greyhammer.erpservice.services.ProjectTargetScheduleService;
import com.greyhammer.erpservice.views.ProjectTargetScheduleView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class ProjectTargetScheduleController {
    private final ProjectTargetScheduleService projectScheduleService;

    public ProjectTargetScheduleController(ProjectTargetScheduleService projectScheduleService) {
        this.projectScheduleService = projectScheduleService;
    }

    @RequestMapping("/api/projects/{id}/target-schedules")
    @JsonView(ProjectTargetScheduleView.FullView.class)
    public ResponseEntity<?> getAllByProject(@PathVariable Long id) {
        try {
            Set<ProjectTargetSchedule> schedules = projectScheduleService.getAllSchedulesByProjectId(id);
            return ResponseEntity.ok().body(schedules);
        } catch (ProjectNotFoundException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Project not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @RequestMapping(value = "/api/projects/{id}/target-schedules", method = RequestMethod.POST)
    @JsonView(ProjectTargetScheduleView.FullView.class)
    public ResponseEntity<?> setTargetProjectSchedule(@RequestBody SetProjectTargetScheduleCommand command) {
        try {
            Set<ProjectTargetSchedule> schedules = projectScheduleService.handleSetProjectTargetScheduleCommand(command);
            return ResponseEntity.ok().body(schedules);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
