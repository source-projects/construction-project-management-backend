package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assignedTo;
    private String createdBy;

    @ManyToOne
    private Project project;

    @Enumerated(value = EnumType.STRING)
    private TaskType type;

}
