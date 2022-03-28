package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
