package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteCostEstimateEvent;
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
public class CompleteCostEstimateEventListener implements ApplicationListener<CompleteCostEstimateEvent> {
    private final TaskService taskService;
    private final ProjectService projectService;

    public CompleteCostEstimateEventListener(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CompleteCostEstimateEvent event) {
        log.debug("Handling complete cost estimate event..");
        Project project = event.getProject();
        project.setStatus(ProjectStatus.ACCOUNTING_APPROVAL);
        projectService.save(project);

        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getProject())
                .type(TaskType.ACCOUNTING_APPROVAL)
                .build();

        taskService.dispatchTask(task);
    }
}
