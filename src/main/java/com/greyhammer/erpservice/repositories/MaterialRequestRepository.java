package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.MaterialRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface MaterialRequestRepository extends PagingAndSortingRepository<MaterialRequest, Long> {
}
