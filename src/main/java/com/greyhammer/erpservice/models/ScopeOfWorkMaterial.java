package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.ScopeOfWorkView;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ScopeOfWorkMaterial {
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

    @JsonView(ScopeOfWorkView.L2View.class)
    private Double price;

    @JsonView(ScopeOfWorkView.L2View.class)
    private Double contingency;

    @JsonView(ScopeOfWorkView.L3View.class)
    private Double laborCost;

    @ManyToOne
    private ScopeOfWorkTask task;

}
