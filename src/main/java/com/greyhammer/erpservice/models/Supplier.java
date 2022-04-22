package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.PurchaseOrderView;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({PurchaseOrderView.FullView.class})
    private Long id;

    @JsonView({PurchaseOrderView.FullView.class})
    private String name;

    @JsonView({PurchaseOrderView.FullView.class})
    private String address1;

    @JsonView({PurchaseOrderView.FullView.class})
    private String address2;

    @JsonView({PurchaseOrderView.FullView.class})
    private String city;

    @JsonView({PurchaseOrderView.FullView.class})
    private String postal;

    @JsonView({PurchaseOrderView.FullView.class})
    private String province;

    @JsonView({PurchaseOrderView.FullView.class})
    private String phone;

    @JsonView({PurchaseOrderView.FullView.class})
    private String email;

    @JsonView({PurchaseOrderView.FullView.class})
    private String createdBy;

    @OneToMany(mappedBy = "supplier")
    private Set<PurchaseOrder> pos;

}
