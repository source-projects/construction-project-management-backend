package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.MaterialRequestView;
import com.greyhammer.erpservice.views.ScopeOfWorkView;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class ScopeOfWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ScopeOfWorkView.L1View.class, MaterialRequestView.FullView.class})
    private Long id;

    @JsonView({ScopeOfWorkView.L1View.class, MaterialRequestView.FullView.class})
    private String name;

    @ManyToOne
    private Project project;

    @JsonView(ScopeOfWorkView.L1View.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scope")
    private Set<ScopeOfWorkTask> tasks;

}
