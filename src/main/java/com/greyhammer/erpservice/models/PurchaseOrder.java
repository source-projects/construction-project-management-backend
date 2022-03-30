package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
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

}
