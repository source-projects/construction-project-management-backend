package com.greyhammer.erpservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProjectSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private ScopeOfWorkTask task;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    private Set<ProjectScheduleBudget> budgets;
}
