package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.TaskInvalidAssignException;
import com.greyhammer.erpservice.exceptions.TaskInvalidStateException;
import com.greyhammer.erpservice.exceptions.TaskNotFoundException;
import com.greyhammer.erpservice.models.Task;

import java.util.Set;

public interface TaskService {
    void dispatchTask(Task task);
    void assignTask(Long id, Set<String> roles, String assignTask) throws TaskNotFoundException, TaskInvalidAssignException;
    void markAsComplete(Long id, String username) throws TaskNotFoundException, TaskInvalidStateException, NoPermissionException;
    Set<Task> getUnassignedTaskByRoles(Set<String> roles);
    Set<Task> getAssignedPendingTask(String assignTo);
    Set<Task> getAssignedCompletedTask(String assignTo);
}
