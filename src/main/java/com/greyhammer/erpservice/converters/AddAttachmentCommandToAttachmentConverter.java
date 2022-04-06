package com.greyhammer.erpservice.converters;

import com.greyhammer.erpservice.commands.AddAttachmentCommand;
import com.greyhammer.erpservice.models.Attachment;
import com.greyhammer.erpservice.models.AttachmentObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class AddAttachmentCommandToAttachmentConverter implements Converter<AddAttachmentCommand, Attachment> {
    @Override
    public Attachment convert(AddAttachmentCommand source) {


        Attachment attachment = new Attachment();
        attachment.setType(source.getType());

        try {
            AttachmentObject object = new AttachmentObject();
            attachment.setObject(object);
            MultipartFile file = source.getFile();
            Byte[] data = new Byte[file.getBytes().length];
            attachment.setMime(file.getContentType());
            attachment.setName(file.getName());

            int i=0;
            for (byte b: file.getBytes()) {
                data[i++] = b;
            }

            object.setData(data);
            object.setAttachment(attachment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attachment;
    }
}
