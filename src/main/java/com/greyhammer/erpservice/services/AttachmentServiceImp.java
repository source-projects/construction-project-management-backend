package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.AddAttachmentCommand;
import com.greyhammer.erpservice.converters.AddAttachmentCommandToAttachmentConverter;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Attachment;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.repositories.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AttachmentServiceImp implements  AttachmentService {
    AddAttachmentCommandToAttachmentConverter addAttachmentCommandToAttachmentConverter;
    AttachmentRepository attachmentRepository;
    ProjectService projectService;

    public AttachmentServiceImp(AddAttachmentCommandToAttachmentConverter addAttachmentCommandToAttachmentConverter, AttachmentRepository attachmentRepository, ProjectService projectService) {
        this.addAttachmentCommandToAttachmentConverter = addAttachmentCommandToAttachmentConverter;
        this.attachmentRepository = attachmentRepository;
        this.projectService = projectService;
    }

    @Override
    @Transactional
    public Attachment handleAddAttachmentCommand(Long projectId, AddAttachmentCommand command) throws ProjectNotFoundException {
        Optional<Project> project = projectService.getProjectById(projectId);

        if (project.isEmpty()) {
            throw new ProjectNotFoundException();
        }

        Attachment attachment = addAttachmentCommandToAttachmentConverter.convert(command);
        attachment.setProject(project.get());

        return attachmentRepository.save(attachment);
    }
}
