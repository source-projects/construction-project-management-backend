package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.ScopeOfWork;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ScopeOfWorkRepository extends CrudRepository<ScopeOfWork, Long> {
    Set<ScopeOfWork> findAllByProjectId(Long projectId);
}
