package com.greyhammer.erpservice.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private String deliveryAddress;
    private String terms;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "po")
    private Set<PurchaseOrderItem> items;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Project project;

    @ManyToOne
    private MaterialRequest request;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PurchaseOrderItem> getItems() {
        return items;
    }

    public void setItems(Set<PurchaseOrderItem> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public MaterialRequest getRequest() {
        return request;
    }

    public void setRequest(MaterialRequest request) {
        this.request = request;
    }
}
