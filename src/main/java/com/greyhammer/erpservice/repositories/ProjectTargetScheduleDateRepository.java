package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.ProjectTargetScheduleDate;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ProjectTargetScheduleDateRepository extends CrudRepository<ProjectTargetScheduleDate, Long> {
    Set<ProjectTargetScheduleDate> findAllByScheduleId(Long scheduleId);
}
