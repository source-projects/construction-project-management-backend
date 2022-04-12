package com.greyhammer.erpservice.listeners;

import com.greyhammer.erpservice.events.CompleteClientApprovalEvent;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.ProjectStatus;
import com.greyhammer.erpservice.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CompleteClientApprovalEventListener implements ApplicationListener<CompleteClientApprovalEvent> {
    private final ProjectService projectService;

    public CompleteClientApprovalEventListener(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public void onApplicationEvent(CompleteClientApprovalEvent event) {
        log.debug("Handling complete client approval event..");
        Project project = event.getProject();
        project.setStatus(ProjectStatus.STARTED);
        projectService.save(project);
    }
}
