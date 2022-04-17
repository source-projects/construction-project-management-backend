package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteFinalMaterialRequestApprovalEvent;
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
public class CompleteFinalMaterialRequestApprovalEventListener
        implements ApplicationListener<CompleteFinalMaterialRequestApprovalEvent> {

    private final TaskService taskService;

    public CompleteFinalMaterialRequestApprovalEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CompleteFinalMaterialRequestApprovalEvent event) {
        log.debug("Handling complete final material request approval event..");
        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getRequest().getProject())
                .materialRequest(event.getRequest())
                .type(TaskType.FOR_PURCHASE_ORDER)
                .build();

        taskService.dispatchTask(task);
    }
}
