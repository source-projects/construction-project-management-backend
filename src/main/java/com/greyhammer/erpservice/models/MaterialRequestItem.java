package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.MaterialRequestView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class MaterialRequestItem {
    @Id
    @JsonView(MaterialRequestView.FullView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MaterialRequest request;

    @JsonView(MaterialRequestView.FullView.class)
    private Double qty;

    @ManyToOne
    @JsonView(MaterialRequestView.FullView.class)
    private ScopeOfWorkMaterial material;
}
