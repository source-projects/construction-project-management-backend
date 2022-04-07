package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CreateProgressReportEvent;
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
public class CreateProgressReportEventListener implements ApplicationListener<CreateProgressReportEvent> {
    private final TaskService taskService;

    public CreateProgressReportEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CreateProgressReportEvent event) {
        log.debug("Handling create progress report event..");
        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getProject())
                .type(TaskType.PROGRESS_APPROVAL)
                .build();

        taskService.dispatchTask(task);
    }
}
