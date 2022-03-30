package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ScopeOfWorkMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String unit;
    private Double qty;
    private Double price;
    private Float contingency;
    private Double laborCost;

    @ManyToOne
    private ScopeOfWorkTask task;

}
