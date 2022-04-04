package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.ProjectView;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProjectView.MinimalView.class})
    private Long id;

    @JsonView({ProjectView.MinimalView.class})
    private String name;
    @JsonView({ProjectView.FullView.class})
    private String address1;
    @JsonView({ProjectView.FullView.class})
    private String address2;
    @JsonView({ProjectView.FullView.class})
    private String city;
    @JsonView({ProjectView.FullView.class})
    private String postal;
    @JsonView({ProjectView.FullView.class})
    private String province;
    @JsonView({ProjectView.FullView.class})
    private String phone;
    @JsonView({ProjectView.FullView.class})
    private String email;

    private String createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Project> projects;

}
