package com.greyhammer.erpservice.converters;

import com.greyhammer.erpservice.commands.DefineScopeOfWorkCommand;
import com.greyhammer.erpservice.models.ScopeOfWork;
import com.greyhammer.erpservice.models.ScopeOfWorkMaterial;
import com.greyhammer.erpservice.models.ScopeOfWorkTask;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DefineScopeOfWorkCommandToScopeOfWorkConverter implements Converter<DefineScopeOfWorkCommand.ScopeCommand, ScopeOfWork> {
    @Override
    public ScopeOfWork convert(DefineScopeOfWorkCommand.ScopeCommand source) {
        ScopeOfWork scope = new ScopeOfWork();
        scope.setName(source.getName());
        Set<ScopeOfWorkTask> tasks = new HashSet<>();


        for (DefineScopeOfWorkCommand.TaskCommand taskCommand : source.getTasks()) {
            ScopeOfWorkTask task = convertTaskCommandToScopeOfWorkTask(taskCommand);
            task.setScope(scope);
            tasks.add(task);
        }

        scope.setTasks(tasks);

        return scope;
    }

    public ScopeOfWorkTask convertTaskCommandToScopeOfWorkTask(DefineScopeOfWorkCommand.TaskCommand source) {
        ScopeOfWorkTask task = new ScopeOfWorkTask();
        task.setName(source.getName());
        task.setUnit(source.getUnit());
        task.setQty(source.getQty());

        Set<ScopeOfWorkMaterial> materials = new HashSet<>();

        for (DefineScopeOfWorkCommand.MaterialCommand materialCommand : source.getMaterials()) {
            ScopeOfWorkMaterial material = convertMaterialCommandToScopeOfWorkMaterial(materialCommand);
            material.setTask(task);
            materials.add(material);
        }

        task.setMaterials(materials);
        return task;
    }

    public ScopeOfWorkMaterial convertMaterialCommandToScopeOfWorkMaterial(
            DefineScopeOfWorkCommand.MaterialCommand source) {
        ScopeOfWorkMaterial material = new ScopeOfWorkMaterial();
        material.setName(source.getName());
        material.setUnit(source.getUnit());
        material.setQty(source.getQty());
        material.setContingency(source.getContingency());
        return material;
    }
}
