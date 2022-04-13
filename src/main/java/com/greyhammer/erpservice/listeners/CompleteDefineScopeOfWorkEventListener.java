package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteDefineScopeOfWorkEvent;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.ProjectStatus;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.models.TaskType;
import com.greyhammer.erpservice.services.ProjectService;
import com.greyhammer.erpservice.services.TaskService;
import com.greyhammer.erpservice.utils.TaskBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CompleteDefineScopeOfWorkEventListener implements ApplicationListener<CompleteDefineScopeOfWorkEvent> {
    private final TaskService taskService;
    private final ProjectService projectService;

    public CompleteDefineScopeOfWorkEventListener(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CompleteDefineScopeOfWorkEvent event) {
        log.debug("Handling complete define scope of work event..");
        Project project = event.getProject();
        project.setStatus(ProjectStatus.COST_ESTIMATE);
        projectService.save(project);

        TaskBuilder builder = new TaskBuilder();
        Task task = builder
            .project(project)
            .type(TaskType.PRICE_CANVASSING)
            .build();
        taskService.dispatchTask(task);

        TaskBuilder operationTaskBuilder = new TaskBuilder();
        Task operationTask = operationTaskBuilder
            .project(project)
            .type(TaskType.SCHEDULE_PROJECT)
            .build();
        taskService.dispatchTask(operationTask);

        TaskBuilder ceTaskBuilder = new TaskBuilder();
        Task ceTask = ceTaskBuilder
            .project(project)
            .type(TaskType.COST_ESTIMATE_APPROVAL)
            .build();
        taskService.dispatchTask(ceTask);
    }
}
