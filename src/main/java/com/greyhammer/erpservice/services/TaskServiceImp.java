package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.TaskInvalidAssignException;
import com.greyhammer.erpservice.exceptions.TaskInvalidStateException;
import com.greyhammer.erpservice.exceptions.TaskNotFoundException;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.models.TaskStatus;
import com.greyhammer.erpservice.models.TaskType;
import com.greyhammer.erpservice.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskTypeService taskTypeService;
    private final TaskEventService taskEventService;

    public TaskServiceImp(TaskRepository taskRepository, TaskTypeService taskTypeService, TaskEventService taskEventService) {
        this.taskRepository = taskRepository;
        this.taskTypeService = taskTypeService;
        this.taskEventService = taskEventService;
    }

    @Override
    public void dispatchTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void assignTask(Long id, Set<String> roles, String assignTo) throws TaskNotFoundException, TaskInvalidAssignException {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty())
            throw new TaskNotFoundException();

        if (optionalTask.get().getAssignedTo() != null) {
            throw new TaskInvalidAssignException();
        }

        Task task = optionalTask.get();
        Set<TaskType> types = taskTypeService.getTaskTypesByRoles(roles);

        if (!types.contains(task.getType())) {
            throw new TaskInvalidAssignException();
        }

        task.setAssignedTo(assignTo);
        taskRepository.save(task);
    }

    @Override
    public void markAsComplete(Long id, String username) throws TaskNotFoundException, TaskInvalidStateException, NoPermissionException {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task task = optionalTask.get();

        if (!task.getAssignedTo().equals(username)) {
            throw new NoPermissionException();
        }

        if (task.getStatus() != TaskStatus.PENDING) {
            throw new TaskInvalidStateException();
        }

        task.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);
        taskEventService.notifyCompletion(task);
    }

    @Override
    public Set<Task> getUnassignedTaskByRoles(Set<String> roles) {
        Set<TaskType> types = taskTypeService.getTaskTypesByRoles(roles);
        return taskRepository.findAllByTypeInAndAssignedToIsNull(types);
    }

    @Override
    public Set<Task> getAssignedPendingTask(String assignTo) {
        return taskRepository.findAllByAssignedToAndStatus(assignTo, TaskStatus.PENDING);
    }

    @Override
    public Set<Task> getAssignedCompletedTask(String assignTo) {
        return taskRepository.findAllByAssignedToAndStatus(assignTo, TaskStatus.COMPLETED);
    }

    @Override
    public Task get(Long id) throws TaskNotFoundException {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return task.get();
    }
}
