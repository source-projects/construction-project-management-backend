package com.greyhammer.erpservice.models;

import javax.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Float getContingency() {
        return contingency;
    }

    public void setContingency(Float contingency) {
        this.contingency = contingency;
    }

    public Double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Double laborCost) {
        this.laborCost = laborCost;
    }

    public ScopeOfWorkTask getTask() {
        return task;
    }

    public void setTask(ScopeOfWorkTask task) {
        this.task = task;
    }
}
