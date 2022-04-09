package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.ProjectView;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.MinimalView.class})
    private Long id;

    @JsonView({ProjectView.MinimalView.class})
    private String name;

    @JsonView({ProjectView.FullView.class})
    private String description;

    @JsonView({ProjectView.FullView.class})
    private Boolean hasExistingDesign;
    private String createdBy;

    @ManyToOne
    @JsonView({ProjectView.MinimalView.class})
    private Customer customer;

    @JsonView({ProjectView.FullView.class})
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Log> logs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    @JsonView({ProjectView.FullView.class})
    private Set<Attachment> attachments;

    @JsonView({ProjectView.FullView.class})
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Task> tasks;

    @JsonView({ProjectView.FullView.class})
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ScopeOfWork> scopes;

    @OneToMany(mappedBy = "project")
    @JsonView({ProjectView.FullView.class})
    private Set<PurchaseOrder> pos;

    @Enumerated(value = EnumType.STRING)
    @JsonView({ProjectView.FullView.class})
    private ProjectDesignStatus designStatus;

    @Enumerated(value = EnumType.STRING)
    @JsonView({ProjectView.MinimalView.class})
    private ProjectStatus status;

}
