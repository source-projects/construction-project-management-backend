package com.greyhammer.erpservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.commands.AddAttachmentCommand;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Attachment;
import com.greyhammer.erpservice.services.AttachmentService;
import com.greyhammer.erpservice.views.AttachmentView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @JsonView(AttachmentView.MetaView.class)
    @RequestMapping("/projects/{projectId}/attachments")
    public ResponseEntity<Set<Attachment>> getAll(@PathVariable Long projectId) {
        return null;
    }

    @JsonView(AttachmentView.MetaView.class)
    @RequestMapping(value = "/projects/{projectId}/attachments", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@PathVariable Long projectId, @ModelAttribute AddAttachmentCommand command) {
        try {
            Attachment attachment = attachmentService.handleAddAttachmentCommand(projectId, command);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(attachment.getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(attachment);

        } catch (ProjectNotFoundException pnfe) {
            log.error(pnfe.toString());
            Map<String, String> response = new HashMap<String, String>();
            response.put("message", "Project not found.");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception ex) {
            log.error(ex.toString());
            Map<String, String> response = new HashMap<String, String>();
            response.put("message", "Something went wrong. Try again later.");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @JsonView(AttachmentView.FullView.class)
    @RequestMapping("/projects/{projectId}/attachments/{attachmentId}")
    public ResponseEntity<Attachment> get(@PathVariable Long projectId, @PathVariable Long attachmentId) {
        return null;
    }
}
