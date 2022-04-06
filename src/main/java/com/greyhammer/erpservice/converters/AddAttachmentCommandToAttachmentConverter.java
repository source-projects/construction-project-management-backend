package com.greyhammer.erpservice.converters;

import com.greyhammer.erpservice.commands.AddAttachmentCommand;
import com.greyhammer.erpservice.models.Attachment;
import com.greyhammer.erpservice.models.AttachmentObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddAttachmentCommandToAttachmentConverter implements Converter<AddAttachmentCommand, Attachment> {
    @Override
    public Attachment convert(AddAttachmentCommand source) {
        Attachment attachment = new Attachment();
        attachment.setName(source.getName());
        attachment.setMime(source.getMime());
        attachment.setType(source.getType());

        AttachmentObject object = new AttachmentObject();
        object.setData(source.getData());
        attachment.setObject(object);
        object.setAttachment(attachment);
        return attachment;
    }
}
