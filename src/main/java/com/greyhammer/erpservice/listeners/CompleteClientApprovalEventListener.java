package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteClientApprovalEvent;
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

@Slf4j
@Component
public class CompleteClientApprovalEventListener implements ApplicationListener<CompleteClientApprovalEvent> {
    private final ProjectService projectService;
    private final TaskService taskService;

    public CompleteClientApprovalEventListener(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public void onApplicationEvent(CompleteClientApprovalEvent event) {
        log.debug("Handling complete client approval event..");
        Project project = event.getProject();

        if (project.getClientApprover() != null) {
            project.setStatus(ProjectStatus.STARTED);
            projectService.save(project);
        } else {
            project.setStatus(ProjectStatus.COST_ESTIMATE);
            projectService.save(project);

            TaskBuilder builder = new TaskBuilder();

            Task task = builder
                    .project(event.getProject())
                    .type(TaskType.COST_ESTIMATE_APPROVAL)
                    .build();

            taskService.dispatchTask(task);
        }
    }
}
