package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class ScopeOfWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String unit;
    private Double qty;
    private Double laborCost;

    @ManyToOne
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scope")
    private Set<ScopeOfWorkTask> tasks;

}
