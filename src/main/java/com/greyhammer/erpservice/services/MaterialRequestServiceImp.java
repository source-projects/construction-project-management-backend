package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.CreateMaterialRequestCommand;
import com.greyhammer.erpservice.converters.CreateMaterialRequestCommandToMaterialRequestConverter;
import com.greyhammer.erpservice.events.CompleteMaterialRequestApprovalEvent;
import com.greyhammer.erpservice.events.CreateMaterialRequestEvent;
import com.greyhammer.erpservice.exceptions.MaterialRequestNotFoundException;
import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.MaterialRequest;
import com.greyhammer.erpservice.models.MaterialRequestStatus;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.repositories.MaterialRequestItemRepository;
import com.greyhammer.erpservice.repositories.MaterialRequestRepository;
import com.greyhammer.erpservice.utils.UserSessionUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class MaterialRequestServiceImp implements MaterialRequestService {
    private final MaterialRequestRepository materialRequestRepository;
    private final MaterialRequestItemRepository materialRequestItemRepository;
    private final ProjectService projectService;
    private final
        CreateMaterialRequestCommandToMaterialRequestConverter converter;

    private final ApplicationEventPublisher applicationEventPublisher;

    public MaterialRequestServiceImp(
            MaterialRequestRepository materialRequestRepository,
            MaterialRequestItemRepository materialRequestItemRepository,
            ProjectService projectService,
            CreateMaterialRequestCommandToMaterialRequestConverter converter,
            ApplicationEventPublisher applicationEventPublisher) {
        this.materialRequestRepository = materialRequestRepository;
        this.materialRequestItemRepository = materialRequestItemRepository;
        this.projectService = projectService;
        this.converter = converter;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Set<MaterialRequest> getAll(Pageable pageable) {
        Set<MaterialRequest> requests = new HashSet<>();
        materialRequestRepository.findAll(pageable).iterator().forEachRemaining(requests::add);
        return requests;
    }

    @Override
    public MaterialRequest get(Long id) throws MaterialRequestNotFoundException {
        Optional<MaterialRequest> request = materialRequestRepository.findById(id);

        if (request.isEmpty())
            throw new MaterialRequestNotFoundException();

        return request.get();
    }

    @Override
    public MaterialRequest approve(Long id) throws MaterialRequestNotFoundException, NoPermissionException {
        Set<String> roles = UserSessionUtil.getCurrentUserRoles();

        if (!roles.contains("qs")) {
            throw new NoPermissionException();
        }

        MaterialRequest request = get(id);

        request.setApprover(UserSessionUtil.getCurrentUsername());
        request.setStatus(MaterialRequestStatus.APPROVED);
        materialRequestRepository.save(request);


        CompleteMaterialRequestApprovalEvent event = new CompleteMaterialRequestApprovalEvent(this);
        event.setRequest(request);
        applicationEventPublisher.publishEvent(event);
        return request;
    }

    @Override
    public MaterialRequest reject(Long id) throws MaterialRequestNotFoundException, NoPermissionException {
        Set<String> roles = UserSessionUtil.getCurrentUserRoles();

        if (!roles.contains("qs")) {
            throw new NoPermissionException();
        }

        MaterialRequest request = get(id);

        request.setStatus(MaterialRequestStatus.REJECTED);
        materialRequestRepository.save(request);
        return request;
    }

    @Override
    public Long getTotalMaterialRequestCount() {
        return materialRequestRepository.count();
    }

    @Override
    public MaterialRequest handleCreateMaterialRequestCommand(CreateMaterialRequestCommand source)
            throws ProjectNotFoundException {
        Project project = projectService.get(source.getProjectId());
        MaterialRequest request = converter.convert(source);
        if (request != null) {
            request.setProject(project);
            materialRequestRepository.save(request);
            request.getItems().iterator().forEachRemaining(materialRequestItemRepository::save);
        }

        CreateMaterialRequestEvent event = new CreateMaterialRequestEvent(this);
        event.setRequest(request);
        applicationEventPublisher.publishEvent(event);
        return request;
    }
}
