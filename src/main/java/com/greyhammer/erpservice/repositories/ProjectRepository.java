package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
}
