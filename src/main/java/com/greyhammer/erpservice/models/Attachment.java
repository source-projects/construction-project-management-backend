package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.AttachmentView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({AttachmentView.MetaView.class})
    private Long id;

    @JsonView({AttachmentView.MetaView.class})
    private String mime;
    @JsonView({AttachmentView.MetaView.class})
    private String name;
    @JsonView({AttachmentView.MetaView.class})
    private String purpose;

    @JsonView({AttachmentView.FullView.class})
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AttachmentObject object;

    @Enumerated(value = EnumType.STRING)
    @JsonView({AttachmentView.MetaView.class})
    private AttachmentType type;

    @ManyToOne
    private Project project;
    
    @ManyToOne
    private Task task;
}
