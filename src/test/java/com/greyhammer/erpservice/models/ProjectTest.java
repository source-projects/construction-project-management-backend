package com.greyhammer.erpservice.models;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    Project project;

    @BeforeEach
    public void setup() {
        project = new Project();
    }

    @Test
    void getId() {
        project.setId(4L);
        assertEquals(4L, project.getId());
    }
}