package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.ProjectTargetScheduleView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProjectTargetSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProjectTargetScheduleView.FullView.class)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    @JsonView(ProjectTargetScheduleView.FullView.class)
    private ScopeOfWorkTask task;

    @JsonView(ProjectTargetScheduleView.FullView.class)
    private Date start;

    @JsonView(ProjectTargetScheduleView.FullView.class)
    private Date end;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    @JsonView(ProjectTargetScheduleView.FullView.class)
    private Set<ProjectTargetScheduleDate> dates;
}
