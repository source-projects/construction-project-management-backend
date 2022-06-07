package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.DefineScopeOfWorkCommand;
import com.greyhammer.erpservice.converters.DefineScopeOfWorkCommandToScopeOfWorkConverter;
import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.ScopeOfWork;
import com.greyhammer.erpservice.models.ScopeOfWorkMaterial;
import com.greyhammer.erpservice.models.ScopeOfWorkTask;
import com.greyhammer.erpservice.repositories.ScopeOfWorkMaterialRepository;
import com.greyhammer.erpservice.repositories.ScopeOfWorkRepository;
import com.greyhammer.erpservice.repositories.ScopeOfWorkTaskRepository;
import com.greyhammer.erpservice.utils.UserSessionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ScopeOfWorkServiceImp implements ScopeOfWorkService {
    private final ScopeOfWorkRepository scopeOfWorkRepository;
    private final ScopeOfWorkTaskRepository scopeOfWorkTaskRepository;
    private final ScopeOfWorkMaterialRepository scopeOfWorkMaterialRepository;
    private final ProjectService projectService;
    private final DefineScopeOfWorkCommandToScopeOfWorkConverter defineScopeOfWorkCommandToScopeOfWorkConverter;

    public ScopeOfWorkServiceImp(
            ScopeOfWorkRepository scopeOfWorkRepository,
            ScopeOfWorkTaskRepository scopeOfWorkTaskRepository,
            ScopeOfWorkMaterialRepository scopeOfWorkMaterialRepository,
            ProjectService projectService,
            DefineScopeOfWorkCommandToScopeOfWorkConverter defineScopeOfWorkCommandToScopeOfWorkConverter) {
        this.scopeOfWorkRepository = scopeOfWorkRepository;
        this.scopeOfWorkTaskRepository = scopeOfWorkTaskRepository;
        this.scopeOfWorkMaterialRepository = scopeOfWorkMaterialRepository;
        this.projectService = projectService;
        this.defineScopeOfWorkCommandToScopeOfWorkConverter = defineScopeOfWorkCommandToScopeOfWorkConverter;
    }

    @Override
    public Set<ScopeOfWork> getAll(Long projectId) throws ProjectNotFoundException {
        Project project = projectService.get(projectId);
        return scopeOfWorkRepository.findAllByProjectId(project.getId());
    }

    @Override
    public void delete(Long id) {
        Optional<ScopeOfWork> scope = scopeOfWorkRepository.findById(id);
        scope.ifPresent(scopeOfWorkRepository::delete);
    }

    @Override
    @Transactional
    public Set<ScopeOfWork> handleDefineScopeOfWorkCommand(Long projectId, DefineScopeOfWorkCommand command)
            throws ProjectNotFoundException {

        Project project = projectService.get(projectId);

        Set<ScopeOfWork> scopes = new HashSet<>();
        for (DefineScopeOfWorkCommand.ScopeCommand scopeCommand : command.getScopes()) {
            if (scopeCommand.getType() == DefineScopeOfWorkCommand.CommandType.CREATE) {
                ScopeOfWork scope = createScopeOfWorkFromCommand(project, scopeCommand);

                if (scope != null) {
                    scopes.add(scope);
                }

            } else if(scopeCommand.getType() == DefineScopeOfWorkCommand.CommandType.UPDATE) {
                ScopeOfWork scope = updateScopeOfWorkFromCommand(scopeCommand);

                if (scope != null) {
                    scopes.add(scope);
                }
            } else if (scopeCommand.getType() == DefineScopeOfWorkCommand.CommandType.DELETE) {
                delete(scopeCommand.getId());
            }
        }

        return scopes;
    }

    @Override
    public ScopeOfWorkTask getTaskById(Long id) {
        Optional<ScopeOfWorkTask> task = scopeOfWorkTaskRepository.findById(id);

        return task.orElse(null);
    }

    @Override
    public ScopeOfWorkMaterial getMaterialById(Long id) {
        Optional<ScopeOfWorkMaterial> material = scopeOfWorkMaterialRepository.findById(id);

        return material.orElse(null);

    }

    private boolean hasQSRolePermission() {
        return UserSessionUtil.getCurrentUserRoles().contains("qs");
    }

    private ScopeOfWork createScopeOfWorkFromCommand(Project project, DefineScopeOfWorkCommand.ScopeCommand command) {
        ScopeOfWork scope = defineScopeOfWorkCommandToScopeOfWorkConverter.convert(command);

        if (scope == null)
            return null;

        scope.setProject(project);
        scopeOfWorkRepository.save(scope);
        for (ScopeOfWorkTask task: scope.getTasks()) {
            scopeOfWorkTaskRepository.save(task);
            scopeOfWorkMaterialRepository.saveAll(task.getMaterials());
        }
        return scope;
    }

    private ScopeOfWork updateScopeOfWorkFromCommand(DefineScopeOfWorkCommand.ScopeCommand command) {
        Optional<ScopeOfWork> optionScope = scopeOfWorkRepository.findById(command.getId());

        if (optionScope.isEmpty()) {
            return null;
        }

        ScopeOfWork scope = optionScope.get();
        scope.setName(command.getName());
        scope.setSubconPrice(command.getSubconPrice());
        scopeOfWorkRepository.save(scope);

        for (DefineScopeOfWorkCommand.TaskCommand taskCommand: command.getTasks()) {
            if (taskCommand.getType() == DefineScopeOfWorkCommand.CommandType.CREATE) {
                ScopeOfWorkTask task = defineScopeOfWorkCommandToScopeOfWorkConverter.convertTaskCommandToScopeOfWorkTask(taskCommand);
                task.setScope(scope);
                scopeOfWorkTaskRepository.save(task);
                scopeOfWorkMaterialRepository.saveAll(task.getMaterials());
            } else if (taskCommand.getType() == DefineScopeOfWorkCommand.CommandType.UPDATE) {
                Optional<ScopeOfWorkTask> optional = scopeOfWorkTaskRepository.findById(taskCommand.getId());
                if (optional.isPresent()) {
                    ScopeOfWorkTask task = optional.get();
                    task.setName(taskCommand.getName());
                    task.setUnit(taskCommand.getUnit());
                    task.setQty(taskCommand.getQty());

                    if (taskCommand.getSubconPricePerUnit() != null) {
                        task.setSubconPricePerUnit(taskCommand.getSubconPricePerUnit());
                    }

                    scopeOfWorkTaskRepository.save(task);
                    updateMaterialsFromCommands(task, taskCommand.getMaterials());
                }
            } else {
                Optional<ScopeOfWorkTask> task = scopeOfWorkTaskRepository.findById(taskCommand.getId());
                task.ifPresent(scopeOfWorkTaskRepository::delete);
            }
        }

        return scopeOfWorkRepository.save(scope);
    }

    private void updateMaterialsFromCommands(ScopeOfWorkTask task,
                                             Set<DefineScopeOfWorkCommand.MaterialCommand> commands) {
        for (DefineScopeOfWorkCommand.MaterialCommand materialCommand: commands) {
            if (materialCommand.getType() == DefineScopeOfWorkCommand.CommandType.CREATE) {
                ScopeOfWorkMaterial material = defineScopeOfWorkCommandToScopeOfWorkConverter
                        .convertMaterialCommandToScopeOfWorkMaterial(materialCommand);
                material.setTask(task);
                scopeOfWorkMaterialRepository.save(material);
            } else if (materialCommand.getType() == DefineScopeOfWorkCommand.CommandType.UPDATE) {
                Optional<ScopeOfWorkMaterial> optional = scopeOfWorkMaterialRepository
                        .findById(materialCommand.getId());
                if (optional.isPresent()) {
                    ScopeOfWorkMaterial material = optional.get();
                    material.setName(materialCommand.getName());
                    material.setUnit(materialCommand.getUnit());
                    material.setQty(material.getQty());
                    material.setContingency(material.getContingency());

                    if (materialCommand.getSubconPricePerUnit() != null) {
                        material.setSubconPricePerUnit(materialCommand.getSubconPricePerUnit());
                    }

                    if (materialCommand.getPricePerUnit() != null) {
                        material.setPricePerUnit(materialCommand.getPricePerUnit());
                    }

                    scopeOfWorkMaterialRepository.save(material);
                }
            } else {
                Optional<ScopeOfWorkMaterial> material = scopeOfWorkMaterialRepository
                        .findById(materialCommand.getId());

                material.ifPresent(scopeOfWorkMaterialRepository::delete);
            }
        }
    }
}
