package com.greyhammer.erpservice.commands;

import com.greyhammer.erpservice.models.AttachmentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddAttachmentCommand {
    private String name;
    private String mime;
    private AttachmentType type;
    private Byte[] data;
}
