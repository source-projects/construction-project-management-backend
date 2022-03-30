package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class ScopeOfWorkTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Set<ScopeOfWorkMaterial> materials;

    @ManyToOne
    private ScopeOfWork scope;

}
