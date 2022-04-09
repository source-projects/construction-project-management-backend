package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.ScopeOfWorkView;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class ScopeOfWorkTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ScopeOfWorkView.L1View.class)
    private Long id;

    @JsonView(ScopeOfWorkView.L1View.class)
    private String name;

    @JsonView(ScopeOfWorkView.L1View.class)
    private String unit;

    @JsonView(ScopeOfWorkView.L1View.class)
    private Double qty;

    @JsonView(ScopeOfWorkView.L3View.class)
    private Double laborCost;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    @JsonView(ScopeOfWorkView.L1View.class)
    private Set<ScopeOfWorkMaterial> materials;

    @ManyToOne
    private ScopeOfWork scope;

}
