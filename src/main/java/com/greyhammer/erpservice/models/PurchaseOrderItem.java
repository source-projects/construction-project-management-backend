package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PurchaseOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String unit;
    private String site;
    private Double qty;
    private Double price;
    private Double received;

    @ManyToOne
    private PurchaseOrder po;

}
