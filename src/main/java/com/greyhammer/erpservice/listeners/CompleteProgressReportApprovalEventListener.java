package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteProgressReportApprovalEvent;
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
public class CompleteProgressReportApprovalEventListener implements ApplicationListener<CompleteProgressReportApprovalEvent> {
    private final TaskService taskService;

    public CompleteProgressReportApprovalEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CompleteProgressReportApprovalEvent event) {
        log.debug("Handling complete progress report approval event..");
        TaskBuilder builder = new TaskBuilder();

        Task task = builder
                .project(event.getProject())
                .type(TaskType.PROGRESS_APPROVAL_CE)
                .build();

        taskService.dispatchTask(task);
    }
}
