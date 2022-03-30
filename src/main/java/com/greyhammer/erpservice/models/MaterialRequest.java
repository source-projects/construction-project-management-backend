package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
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

}
