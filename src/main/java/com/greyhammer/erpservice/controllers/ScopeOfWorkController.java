package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.commands.DefineScopeOfWorkCommand;
import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.ScopeOfWork;
import com.greyhammer.erpservice.services.ScopeOfWorkService;
import com.greyhammer.erpservice.views.ScopeOfWorkView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
public class ScopeOfWorkController {
    private final ScopeOfWorkService scopeOfWorkService;

    public ScopeOfWorkController(ScopeOfWorkService scopeOfWorkService) {
        this.scopeOfWorkService = scopeOfWorkService;
    }

    @JsonView(ScopeOfWorkView.L3View.class)
    @RequestMapping(value = "/api/projects/{projectId}/scope-of-work")
    public ResponseEntity<?> getAll(@PathVariable Long projectId) {
        try {
            Set<ScopeOfWork> scopes = scopeOfWorkService.getAll(projectId);
            return ResponseEntity.ok().body(scopes);
        } catch (ProjectNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @JsonView(ScopeOfWorkView.L3View.class)
    @RequestMapping(value = "/api/projects/{projectId}/scope-of-work/define", method = RequestMethod.POST)
    public ResponseEntity<?> define(@PathVariable Long projectId, @RequestBody DefineScopeOfWorkCommand command) {
        try {
            Set<ScopeOfWork> scopes = scopeOfWorkService.handleDefineScopeOfWorkCommand(projectId, command);
            return ResponseEntity.ok().body(scopes);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
