package com.greyhammer.erpservice.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class MaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String requestBy;
    private Date date;

    @ManyToOne
    private ScopeOfWorkTask task;

    @OneToMany(mappedBy = "request")
    private Set<PurchaseOrder> pos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScopeOfWorkTask getTask() {
        return task;
    }

    public void setTask(ScopeOfWorkTask task) {
        this.task = task;
    }

    public Set<PurchaseOrder> getPos() {
        return pos;
    }

    public void setPos(Set<PurchaseOrder> pos) {
        this.pos = pos;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
