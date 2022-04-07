package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteDefineScopeOfWorkEvent;
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
public class CompleteDefineScopeOfWorkEventListener implements ApplicationListener<CompleteDefineScopeOfWorkEvent> {
    private final TaskService taskService;

    public CompleteDefineScopeOfWorkEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CompleteDefineScopeOfWorkEvent event) {
        log.debug("Handling complete define scope of work event..");
        TaskBuilder builder = new TaskBuilder();

        Task task = builder
            .project(event.getProject())
            .type(TaskType.PRICE_CANVASSING)
            .build();
        taskService.dispatchTask(task);

        builder = new TaskBuilder();

        task = builder
            .project(event.getProject())
            .type(TaskType.SCHEDULE_PROJECT)
            .build();
        taskService.dispatchTask(task);

        task = builder
            .project(event.getProject())
            .type(TaskType.COST_ESTIMATE_APPROVAL)
            .build();
        taskService.dispatchTask(task);
    }
}
