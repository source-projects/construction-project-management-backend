package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.AddAttachmentCommand;
import com.greyhammer.erpservice.exceptions.AttachmentNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotMatchException;
import com.greyhammer.erpservice.exceptions.TaskNotFoundException;
import com.greyhammer.erpservice.models.Attachment;

import java.util.Optional;

public interface AttachmentService {
    Attachment handleAddAttachmentCommand(Long projectId, AddAttachmentCommand command) throws ProjectNotFoundException, TaskNotFoundException;
    void deleteAttachment(Long projectId, Long attachmentId) throws AttachmentNotFoundException, ProjectNotMatchException;
    Attachment get(Long projectId, Long attachmentId) throws AttachmentNotFoundException, ProjectNotMatchException;
}
