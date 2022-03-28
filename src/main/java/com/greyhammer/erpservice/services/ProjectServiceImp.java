package com.greyhammer.erpservice.services;

import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImp implements ProjectService {
    public ProjectServiceImp() {
    }

    public String getProject() {
        return "Hello world!";
    }
}
