package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.MaterialRequestView;
import com.greyhammer.erpservice.views.TaskView;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class MaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({MaterialRequestView.ListView.class, TaskView.ListView.class})
    private Long id;
    @JsonView(MaterialRequestView.ListView.class)
    private String requestBy;
    @JsonView(MaterialRequestView.ListView.class)
    private Date date;

    @JsonView(MaterialRequestView.ListView.class)
    private String approver;

    @JsonView(MaterialRequestView.ListView.class)
    private String finalApprover;

    @ManyToOne
    @JsonView(MaterialRequestView.ListView.class)
    private Project project;

    @ManyToOne
    @JsonView(MaterialRequestView.ListView.class)
    private ScopeOfWorkTask task;

    @JsonView(MaterialRequestView.FullView.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    private Set<MaterialRequestItem> items;

    @OneToMany(mappedBy = "request")
    private Set<PurchaseOrder> pos;

    @Enumerated(value = EnumType.STRING)
    @JsonView(MaterialRequestView.ListView.class)
    private MaterialRequestStatus status;
}
