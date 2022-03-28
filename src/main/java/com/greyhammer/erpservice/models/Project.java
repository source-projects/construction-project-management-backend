package com.greyhammer.erpservice.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Boolean hasExistingDesign;
    private String createdBy;

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Log> logs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Attachment> attachments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ScopeOfWork> scopes;

    @OneToMany(mappedBy = "project")
    private Set<PurchaseOrder> pos;

    @Enumerated(value = EnumType.STRING)
    private ProjectStatus status;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasExistingDesign() {
        return hasExistingDesign;
    }

    public void setHasExistingDesign(Boolean hasExistingDesign) {
        this.hasExistingDesign = hasExistingDesign;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<ScopeOfWork> getScopes() {
        return scopes;
    }

    public void setScopes(Set<ScopeOfWork> scopes) {
        this.scopes = scopes;
    }

    public Set<PurchaseOrder> getPos() {
        return pos;
    }

    public void setPos(Set<PurchaseOrder> pos) {
        this.pos = pos;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
}
