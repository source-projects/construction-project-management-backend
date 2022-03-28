package com.greyhammer.erpservice.models;

import javax.persistence.*;
import java.util.Set;

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

    public Set<ScopeOfWorkMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<ScopeOfWorkMaterial> materials) {
        this.materials = materials;
    }

    public ScopeOfWork getScope() {
        return scope;
    }

    public void setScope(ScopeOfWork scope) {
        this.scope = scope;
    }
}
