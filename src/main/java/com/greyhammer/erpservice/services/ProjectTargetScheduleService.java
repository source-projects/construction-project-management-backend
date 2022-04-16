package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.ProjectTargetSchedule;

import java.util.Set;

public interface ProjectTargetScheduleService {
    Set<ProjectTargetSchedule> getAllSchedulesByProjectId(Long projectId) throws ProjectNotFoundException;
}
