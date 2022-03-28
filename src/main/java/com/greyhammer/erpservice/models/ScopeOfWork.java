package com.greyhammer.erpservice.models;

import javax.persistence.*;
import java.util.Set;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Double laborCost) {
        this.laborCost = laborCost;
    }

    public Set<ScopeOfWorkTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<ScopeOfWorkTask> tasks) {
        this.tasks = tasks;
    }
}
