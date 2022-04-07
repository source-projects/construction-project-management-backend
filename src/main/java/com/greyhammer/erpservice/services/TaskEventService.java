package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.models.Task;

public interface TaskEventService {
    void notifyCompletion(Task task);
}
