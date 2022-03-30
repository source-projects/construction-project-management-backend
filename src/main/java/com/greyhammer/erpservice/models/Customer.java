package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address1;
    private String address2;
    private String city;
    private String postal;
    private String province;
    private String phone;
    private String email;
    private String createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Project> projects;

}
