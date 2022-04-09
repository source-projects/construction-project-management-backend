package com.greyhammer.erpservice.commands;

import com.greyhammer.erpservice.models.AttachmentType;
import com.greyhammer.erpservice.models.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class AddAttachmentCommand {
    private AttachmentType type;
    private MultipartFile file;
    private Long taskId;
}
