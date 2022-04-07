package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.models.TaskStatus;
import com.greyhammer.erpservice.models.TaskType;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Set<Task> findAllByTypeInAndAssignedToIsNull(Set<TaskType> types);
    Set<Task> findAllByAssignedToAndStatus(String assignTo, TaskStatus status);
}
