package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.events.*;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.models.TaskType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TaskEventServiceImp implements TaskEventService {
    private final ApplicationEventPublisher applicationEventPublisher;

    public TaskEventServiceImp(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void notifyCompletion(Task task) {
        TaskEvent event = getCompletionEventByTaskType(task.getType());

        if (event != null) {
            event.setProject(task.getProject());
            applicationEventPublisher.publishEvent(event);
        }
    }

    private TaskEvent getCompletionEventByTaskType(TaskType taskType) {
        if (taskType == TaskType.FOR_ARCHITECTURAL_DESIGN) {
            return new CompleteProjectDesignEvent(this);
        } else if (taskType == TaskType.DEFINE_SCOPE_OF_WORK) {
            return new CompleteDefineScopeOfWorkEvent(this);
        } else if (taskType == TaskType.COST_ESTIMATE_APPROVAL) {
            return new CompleteCostEstimateEvent(this);
        } else if (taskType == TaskType.ACCOUNTING_APPROVAL) {
            return new CompleteAccountingApprovalEvent(this);
        } else if (taskType == TaskType.STATEKHOLDER_APPROVAL) {
            return new CompleteStakeholderApprovalEvent(this);
        } else if (taskType == TaskType.CLIENT_APPROVAL) {
            return new CompleteClientApprovalEvent(this);
        } else if (taskType == TaskType.MATERIAL_REQUEST_APPROVAL) {
            return new CompleteMaterialRequestApprovalEvent(this);
        } else if (taskType == TaskType.PROGRESS_APPROVAL) {
            return new CompleteProgressReportApprovalEvent(this);
        }

        return  null;
    }
}
