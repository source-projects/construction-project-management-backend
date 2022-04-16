package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.ProjectTargetScheduleView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class ProjectTargetScheduleDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProjectTargetScheduleView.FullView.class)
    private Long id;

    @ManyToOne
    private ProjectTargetSchedule schedule;

    @ManyToOne
    @JsonView(ProjectTargetScheduleView.FullView.class)
    private ScopeOfWorkMaterial material;

    @JsonView(ProjectTargetScheduleView.FullView.class)
    private Date date;

    @JsonView(ProjectTargetScheduleView.FullView.class)
    private Float target;
}
