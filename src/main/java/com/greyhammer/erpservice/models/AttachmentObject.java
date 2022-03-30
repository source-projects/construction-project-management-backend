package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AttachmentObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private Byte[] data;

    @OneToOne
    private Attachment attachment;

}
