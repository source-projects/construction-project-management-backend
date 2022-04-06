package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.AttachmentView;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AttachmentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({AttachmentView.FullView.class})
    private Long id;

    @Lob
    @JsonView({AttachmentView.FullView.class})
    private Byte[] data;

    @OneToOne
    private Attachment attachment;

}
