package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.AttachmentView;
import com.greyhammer.erpservice.views.ProjectView;
import com.greyhammer.erpservice.views.TaskView;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({TaskView.ListView.class, ProjectView.FullView.class})
    private Long id;

    @JsonView({TaskView.ListView.class, ProjectView.FullView.class})
    private String assignedTo;

    @JsonView(TaskView.ListView.class)
    private Date date;

    private String createdBy;

    @ManyToOne
    @JsonView(TaskView.ListView.class)
    private Project project;


    @OneToOne
    @JsonView(TaskView.ListView.class)
    private MaterialRequest materialRequest;

    @Enumerated(value = EnumType.STRING)
    @JsonView({TaskView.ListView.class, ProjectView.FullView.class})
    private TaskType type;

    @Enumerated(value = EnumType.STRING)
    @JsonView({TaskView.ListView.class, ProjectView.FullView.class})
    private TaskStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Set<Attachment> attachments;
}
