package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteStakeholderApprovalEvent;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.models.TaskType;
import com.greyhammer.erpservice.services.TaskService;
import com.greyhammer.erpservice.utils.TaskBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CompleteStakeholderApprovalEventListener implements ApplicationListener<CompleteStakeholderApprovalEvent> {
    private final TaskService taskService;

    public CompleteStakeholderApprovalEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void onApplicationEvent(CompleteStakeholderApprovalEvent event) {
        log.debug("Handling complete stakeholder approval event..");
        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getProject())
                .type(TaskType.CLIENT_APPROVAL)
                .build();

        taskService.dispatchTask(task);
    }
}
