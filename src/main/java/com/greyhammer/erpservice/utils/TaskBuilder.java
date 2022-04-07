package com.greyhammer.erpservice.utils;

import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.models.TaskStatus;
import com.greyhammer.erpservice.models.TaskType;

public class TaskBuilder {
    private Task task;

    public TaskBuilder() {
        this.task = new Task();
    }

    public TaskBuilder project(Project project) {
        task.setProject(project);
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
