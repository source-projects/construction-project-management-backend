package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.commands.SetProjectDesignStatusCommand;
import com.greyhammer.erpservice.exceptions.ProjectInvalidStateException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.services.ProjectService;
import com.greyhammer.erpservice.views.ProjectView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class DesignStatusController {
    private final ProjectService projectService;

    public DesignStatusController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @JsonView(ProjectView.MinimalView.class)
    @RequestMapping(value = "/api/projects/{projectId}/design-status", method = RequestMethod.PUT)
    public ResponseEntity<?> set(@PathVariable Long projectId, @ModelAttribute SetProjectDesignStatusCommand command) {
        try {
            command.setProjectId(projectId);
            return ResponseEntity.ok(projectService.handleSetDesignStatusCommand(command));
        } catch (ProjectInvalidStateException ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cannot set design status.");
            return ResponseEntity.internalServerError().body(response);
        } catch (ProjectNotFoundException ex) {
            log.error(ex.toString());
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
