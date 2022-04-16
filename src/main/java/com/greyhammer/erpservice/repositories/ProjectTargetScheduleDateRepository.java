package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.ProjectTargetScheduleDate;
import org.springframework.data.repository.CrudRepository;

public interface ProjectTargetScheduleDateRepository extends CrudRepository<ProjectTargetScheduleDate, Long> {
    void deleteAllByScheduleId(Long scheduleId);
}
