package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.ProjectView;
import com.greyhammer.erpservice.views.TaskView;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TaskView.ListView.class)
    private Long id;

    @JsonView(TaskView.ListView.class)
    private String assignedTo;
    private String createdBy;

    @ManyToOne
    @JsonView(TaskView.ListView.class)
    private Project project;

    @Enumerated(value = EnumType.STRING)
    @JsonView(TaskView.ListView.class)
    private TaskType type;

    @Enumerated(value = EnumType.STRING)
    @JsonView(TaskView.ListView.class)
    private TaskStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Set<Attachment> attachments;
}
