package com.greyhammer.erpservice.utils;

import com.greyhammer.erpservice.models.*;

public class TaskBuilder {
    private Task task;

    public TaskBuilder() {
        this.task = new Task();
    }

    public TaskBuilder project(Project project) {
        task.setProject(project);
        return this;
    }

    public TaskBuilder materialRequest(MaterialRequest request) {
        task.setMaterialRequest(request);
        return this;
    }

    public TaskBuilder type(TaskType type) {
        task.setType(type);
        return this;
    }

    public Task build() {
        task.setStatus(TaskStatus.PENDING);
        return task;
    }
}
