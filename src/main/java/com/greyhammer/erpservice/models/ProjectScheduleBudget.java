package com.greyhammer.erpservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class ProjectScheduleBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProjectSchedule schedule;

    @ManyToOne
    private ScopeOfWorkMaterial material;

    private Date date;
    private Float budget;
}
