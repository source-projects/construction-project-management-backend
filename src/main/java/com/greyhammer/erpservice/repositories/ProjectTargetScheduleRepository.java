package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.ProjectTargetSchedule;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ProjectTargetScheduleRepository extends CrudRepository<ProjectTargetSchedule, Long> {
    Set<ProjectTargetSchedule> findAllByProjectId(Long projectId);
}
