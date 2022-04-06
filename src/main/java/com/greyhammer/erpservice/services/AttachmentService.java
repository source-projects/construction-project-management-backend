package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.AddAttachmentCommand;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Attachment;

public interface AttachmentService {
    Attachment handleAddAttachmentCommand(Long projectId, AddAttachmentCommand command) throws ProjectNotFoundException;
}
