package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.DefineScopeOfWorkCommand;
import com.greyhammer.erpservice.converters.DefineScopeOfWorkCommandToScopeOfWorkConverter;
import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.ScopeOfWork;
import com.greyhammer.erpservice.models.ScopeOfWorkMaterial;
import com.greyhammer.erpservice.models.ScopeOfWorkTask;
import com.greyhammer.erpservice.repositories.ScopeOfWorkRepository;
import com.greyhammer.erpservice.utils.UserSessionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ScopeOfWorkServiceImp implements ScopeOfWorkService {
    private final ScopeOfWorkRepository scopeOfWorkRepository;
    private final ProjectService projectService;
    private final DefineScopeOfWorkCommandToScopeOfWorkConverter defineScopeOfWorkCommandToScopeOfWorkConverter;

    public ScopeOfWorkServiceImp(
            ScopeOfWorkRepository scopeOfWorkRepository,
            ProjectService projectService,
            DefineScopeOfWorkCommandToScopeOfWorkConverter defineScopeOfWorkCommandToScopeOfWorkConverter) {
        this.scopeOfWorkRepository = scopeOfWorkRepository;
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
    public void handleDefineScopeOfWorkCommand(Long projectId, DefineScopeOfWorkCommand command)
            throws ProjectNotFoundException, NoPermissionException {
        if (!hasQSRolePermission()) {
            throw new NoPermissionException();
        }

        Project project = projectService.get(projectId);

        for (DefineScopeOfWorkCommand.ScopeCommand scopeCommand : command.getScopes()) {
            if (scopeCommand.getType() == DefineScopeOfWorkCommand.CommandType.CREATE) {
                scopeCommand.setProjectId(project.getId());
                createScopeOfWorkFromCommand(scopeCommand);
            } else if(scopeCommand.getType() == DefineScopeOfWorkCommand.CommandType.UPDATE) {
                updateScopeOfWorkFromCommand(scopeCommand);
            } else if (scopeCommand.getType() == DefineScopeOfWorkCommand.CommandType.DELETE) {
                delete(scopeCommand.getId());
            }
        }
    }

    private boolean hasQSRolePermission() {
        return UserSessionUtil.getCurrentUserRoles().contains("qs");
    }

    private void createScopeOfWorkFromCommand(DefineScopeOfWorkCommand.ScopeCommand command) {
        ScopeOfWork scope = defineScopeOfWorkCommandToScopeOfWorkConverter.convert(command);

        if (scope != null) {
            scopeOfWorkRepository.save(scope);
        }
    }

    private void updateScopeOfWorkFromCommand(DefineScopeOfWorkCommand.ScopeCommand command) {
        Optional<ScopeOfWork> optionScope = scopeOfWorkRepository.findById(command.getId());

        if (optionScope.isEmpty()) {
            return;
        }

        ScopeOfWork scope = optionScope.get();
        scope.setName(command.getName());

        Map<Long, DefineScopeOfWorkCommand.TaskCommand> taskCommandDictionary
                = convertCommandsToDictionary(command.getTasks());

        for (ScopeOfWorkTask task : scope.getTasks()) {
            DefineScopeOfWorkCommand.TaskCommand taskCommand = taskCommandDictionary.get(task.getId());

            if (taskCommand != null) {
                task.setName(taskCommand.getName());
                task.setUnit(taskCommand.getUnit());
                task.setQty(taskCommand.getQty());
                updateMaterialsFromCommands(task, taskCommand.getMaterials());
            }
        }

        scopeOfWorkRepository.save(scope);
    }

    private <T extends DefineScopeOfWorkCommand.Command>Map<Long, T> convertCommandsToDictionary(
            Set<T> commands) {
        Map<Long, T> dictionary = new HashMap<>();

        for (T command: commands) {
            if (command.getId() != null) {
                dictionary.put(command.getId(), command);
            }
        }

        return dictionary;
    }

    private void updateMaterialsFromCommands(
            ScopeOfWorkTask task,
            Set<DefineScopeOfWorkCommand.MaterialCommand> commands) {
        Map<Long, DefineScopeOfWorkCommand.MaterialCommand> materialCommandDictionary =
                convertCommandsToDictionary(commands);

        for (ScopeOfWorkMaterial material : task.getMaterials()) {
            DefineScopeOfWorkCommand.MaterialCommand command = materialCommandDictionary.get(material.getId());

            if (command != null) {
                material.setName(command.getName());
                material.setUnit(command.getUnit());
                material.setQty(command.getQty());
                material.setContingency(command.getContingency());
            }
        }
    }
}
