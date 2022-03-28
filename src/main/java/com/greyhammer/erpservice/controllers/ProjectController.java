package com.greyhammer.erpservice.controllers;

import com.greyhammer.erpservice.services.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String get() {
        return service.getProject();
    }
}
