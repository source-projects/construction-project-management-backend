package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.TaskInvalidAssignException;
import com.greyhammer.erpservice.exceptions.TaskInvalidStateException;
import com.greyhammer.erpservice.exceptions.TaskNotFoundException;
import com.greyhammer.erpservice.models.Task;

import java.util.Set;

public interface TaskService {
    void dispatchTask(Task task);
    void assignTask(Long id) throws TaskNotFoundException, TaskInvalidAssignException;
    void markAsComplete(Long id) throws TaskNotFoundException, TaskInvalidStateException, NoPermissionException;
    Set<Task> getUnassignedTask();
    Set<Task> getAssignedPendingTask();
    Set<Task> getAssignedCompletedTask();
    Task get(Long id) throws TaskNotFoundException;
}
