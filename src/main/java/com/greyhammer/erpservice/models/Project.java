package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Boolean hasExistingDesign;
    private String createdBy;

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Log> logs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Attachment> attachments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ScopeOfWork> scopes;

    @OneToMany(mappedBy = "project")
    private Set<PurchaseOrder> pos;

    @Enumerated(value = EnumType.STRING)
    private ProjectStatus status;

}
