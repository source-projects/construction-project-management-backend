package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.CreateMaterialRequestCommand;
import com.greyhammer.erpservice.exceptions.MaterialRequestNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.MaterialRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface MaterialRequestService {
    Set<MaterialRequest> getAll(Pageable pageable);
    MaterialRequest get(Long id) throws MaterialRequestNotFoundException;
    Long getTotalMaterialRequestCount();
    MaterialRequest handleCreateMaterialRequestCommand(CreateMaterialRequestCommand source) throws ProjectNotFoundException;
}
