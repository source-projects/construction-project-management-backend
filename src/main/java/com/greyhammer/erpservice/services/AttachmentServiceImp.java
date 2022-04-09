package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.AddAttachmentCommand;
import com.greyhammer.erpservice.converters.AddAttachmentCommandToAttachmentConverter;
import com.greyhammer.erpservice.exceptions.AttachmentNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotMatchException;
import com.greyhammer.erpservice.exceptions.TaskNotFoundException;
import com.greyhammer.erpservice.models.Attachment;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.Task;
import com.greyhammer.erpservice.repositories.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class AttachmentServiceImp implements  AttachmentService {
    AddAttachmentCommandToAttachmentConverter addAttachmentCommandToAttachmentConverter;
    AttachmentRepository attachmentRepository;
    ProjectService projectService;
    TaskService taskService;

    public AttachmentServiceImp(
            AddAttachmentCommandToAttachmentConverter addAttachmentCommandToAttachmentConverter,
            AttachmentRepository attachmentRepository,
            ProjectService projectService,
            TaskService taskService) {
        this.addAttachmentCommandToAttachmentConverter = addAttachmentCommandToAttachmentConverter;
        this.attachmentRepository = attachmentRepository;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public Attachment handleAddAttachmentCommand(Long projectId, AddAttachmentCommand command) throws ProjectNotFoundException, TaskNotFoundException {
        Project project = projectService.get(projectId);

        Attachment attachment = addAttachmentCommandToAttachmentConverter.convert(command);

        if (attachment != null) {
            attachment.setProject(project);

            if (command.getTaskId() != null) {
                Task task = taskService.get(command.getTaskId());
                attachment.setTask(task);
            }

            return attachmentRepository.save(attachment);
        }

        return null;
    }

    @Override
    public void deleteAttachment(Long projectId, Long attachmentId) throws AttachmentNotFoundException, ProjectNotMatchException {
        Attachment attachment = get(projectId, attachmentId);
        attachmentRepository.delete(attachment);
    }

    @Override
    public Attachment get(Long projectId, Long attachmentId) throws AttachmentNotFoundException, ProjectNotMatchException {
        Optional<Attachment> attachment = attachmentRepository.findById(attachmentId);

        if (attachment.isEmpty())
            throw new AttachmentNotFoundException();

        if (!attachment.get().getProject().getId().equals(projectId)) {
            throw new ProjectNotMatchException();
        }

        return attachment.get();
    }

    @Override
    public Set<Attachment> getAllByProjectId(Long projectId) {
        return attachmentRepository.findAllByProjectId(projectId);
    }

    @Override
    public Set<Attachment> getAllByProjectIdAndTaskId(Long projectId, Long taskId) {
        return attachmentRepository.findAllByProjectIdAndTaskId(projectId, taskId);
    }
}
