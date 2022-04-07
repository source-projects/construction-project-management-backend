package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CreateProjectEvent;
import com.greyhammer.erpservice.models.Project;
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
public class CreateProjectEventListener implements ApplicationListener<CreateProjectEvent> {
    private final TaskService taskService;

    public CreateProjectEventListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CreateProjectEvent event) {
        log.debug("Handling create project event..");

        Project project = event.getProject();

        TaskBuilder builder = new TaskBuilder();

        Task task = builder
            .project(event.getProject())
            .type(project.getHasExistingDesign()
                ? TaskType.DEFINE_SCOPE_OF_WORK
                : TaskType.FOR_ARCHITECTURAL_DESIGN)
            .build();

        taskService.dispatchTask(task);
    }
}
