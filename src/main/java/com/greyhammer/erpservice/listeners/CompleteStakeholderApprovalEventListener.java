package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteStakeholderApprovalEvent;
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
public class CompleteStakeholderApprovalEventListener implements ApplicationListener<CompleteStakeholderApprovalEvent> {
    private final TaskService taskService;
    private final ProjectService projectService;

    public CompleteStakeholderApprovalEventListener(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @Override
    public void onApplicationEvent(CompleteStakeholderApprovalEvent event) {
        log.debug("Handling complete stakeholder approval event..");
        Project project = event.getProject();
        project.setStatus(ProjectStatus.CLIENT_APPROVAL);
        projectService.save(project);

        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getProject())
                .type(TaskType.CLIENT_APPROVAL)
                .build();

        taskService.dispatchTask(task);
    }
}
