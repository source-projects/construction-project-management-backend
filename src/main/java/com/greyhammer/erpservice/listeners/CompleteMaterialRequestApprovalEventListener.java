package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteMaterialRequestApprovalEvent;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.models.TaskType;
import com.greyhammer.erpservice.services.TaskService;
import com.greyhammer.erpservice.utils.TaskBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CompleteMaterialRequestApprovalEventListener implements ApplicationListener<CompleteMaterialRequestApprovalEvent> {
    private final TaskService taskService;

    public CompleteMaterialRequestApprovalEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CompleteMaterialRequestApprovalEvent event) {
        log.debug("Handling complete material request approval event..");
        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getProject())
                .materialRequest(event.getRequest())
                .type(TaskType.MATERIAL_REQUEST_APPROVAL_CE)
                .build();

        taskService.dispatchTask(task);
    }
}
