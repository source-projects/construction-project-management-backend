package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.DefineScopeOfWorkCommand;
import com.greyhammer.erpservice.exceptions.NoPermissionException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.ScopeOfWork;

import java.util.Set;

public interface ScopeOfWorkService {
    Set<ScopeOfWork> getAll(Long projectId) throws ProjectNotFoundException;
    void delete(Long id);
    Set<ScopeOfWork> handleDefineScopeOfWorkCommand(Long projectId, DefineScopeOfWorkCommand command) throws ProjectNotFoundException, NoPermissionException;
}
