package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CreateMaterialRequestEvent;
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
public class CreateMaterialRequestEventListener implements ApplicationListener<CreateMaterialRequestEvent> {
    private final TaskService taskService;

    public CreateMaterialRequestEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CreateMaterialRequestEvent event) {
        log.debug("Handling create material request event..");
        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getRequest().getProject())
                .materialRequest(event.getRequest())
                .type(TaskType.MATERIAL_REQUEST_APPROVAL)
                .build();

        taskService.dispatchTask(task);
    }
}
