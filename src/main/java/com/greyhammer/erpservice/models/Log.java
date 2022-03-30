package com.greyhammer.erpservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Date timestamp;

    @ManyToOne
    private Project project;

}
