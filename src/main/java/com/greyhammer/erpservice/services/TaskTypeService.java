package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.models.TaskType;

import java.util.Set;

public interface TaskTypeService {
    Set<TaskType> getTaskTypesByRoles(Set<String> roles);
}
