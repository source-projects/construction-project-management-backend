package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteProjectDesignEvent;
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
public class CompleteProjectDesignEventListener implements ApplicationListener<CompleteProjectDesignEvent> {
    private final TaskService taskService;

    public CompleteProjectDesignEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CompleteProjectDesignEvent event) {
        log.debug("Handling complete project design event..");
        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getProject())
                .type(TaskType.DEFINE_SCOPE_OF_WORK)
                .build();

        taskService.dispatchTask(task);
    }
}
