package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.MaterialRequestView;
import com.greyhammer.erpservice.views.ProjectTargetScheduleView;
import com.greyhammer.erpservice.views.ScopeOfWorkView;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class ScopeOfWorkMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({
            ScopeOfWorkView.L1View.class,
            ProjectTargetScheduleView.FullView.class,
            MaterialRequestView.FullView.class
    })
    private Long id;

    @JsonView({
            ScopeOfWorkView.L1View.class,
            ProjectTargetScheduleView.FullView.class,
            MaterialRequestView.FullView.class
    })
    private String name;

    @JsonView({ScopeOfWorkView.L1View.class, MaterialRequestView.FullView.class})
    private String unit;

    @JsonView({ScopeOfWorkView.L1View.class, MaterialRequestView.FullView.class})
    private Double qty;

    @JsonView({ScopeOfWorkView.L2View.class, MaterialRequestView.FullView.class})
    private Double contingency;

    @JsonView(ScopeOfWorkView.L2View.class)
    private Double pricePerUnit;

    @JsonView(ScopeOfWorkView.L3View.class)
    private Double subconPricePerUnit;

    @JsonView({ScopeOfWorkView.L3View.class, MaterialRequestView.FullView.class})
    private Double released;

    @ManyToOne
    private ScopeOfWorkTask task;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
    private Set<ProjectTargetScheduleDate> budgets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
    private Set<MaterialRequestItem> requestItems;
}
