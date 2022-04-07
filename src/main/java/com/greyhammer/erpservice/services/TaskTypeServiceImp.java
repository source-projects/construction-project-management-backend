package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.models.TaskType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class TaskTypeServiceImp implements TaskTypeService {
    private static final Map<String, Set<TaskType>> taskTypeDictionary = new HashMap<>();

    public TaskTypeServiceImp() {
        buildDictionary();
    }

    @Override
    public Set<TaskType> getTaskTypesByRoles(Set<String> roles) {
        Set<TaskType> types = new HashSet<>();

        for(String role: roles) {
            Set<TaskType> subset = taskTypeDictionary.get(role);

            if (subset != null) {
                types.addAll(subset);
            }
        }

        return types;
    }

    private void buildDictionary() {
        Set<TaskType> designTasks = new HashSet<>();
        designTasks.add(TaskType.FOR_ARCHITECTURAL_DESIGN);
        taskTypeDictionary.put("design", designTasks);

        Set<TaskType> qsTasks = new HashSet<>();
        qsTasks.add(TaskType.DEFINE_SCOPE_OF_WORK);
        qsTasks.add(TaskType.PROGRESS_APPROVAL);
        qsTasks.add(TaskType.MATERIAL_REQUEST_APPROVAL);
        taskTypeDictionary.put("qs", qsTasks);

        Set<TaskType> procurementTasks = new HashSet<>();
        procurementTasks.add(TaskType.PRICE_CANVASSING);
        procurementTasks.add(TaskType.FOR_PURCHASE_ORDER);
        taskTypeDictionary.put("procurement", procurementTasks);

        Set<TaskType> operationTasks = new HashSet<>();
        operationTasks.add(TaskType.SCHEDULE_PROJECT);
        taskTypeDictionary.put("operation", operationTasks);

        Set<TaskType> ceTasks = new HashSet<>();
        ceTasks.add(TaskType.MATERIAL_REQUEST_APPROVAL_CE);
        ceTasks.add(TaskType.PROGRESS_APPROVAL_CE);
        ceTasks.add(TaskType.COST_ESTIMATE_APPROVAL);
        taskTypeDictionary.put("ce", ceTasks);

        Set<TaskType> accountingTasks = new HashSet<>();
        accountingTasks.add(TaskType.ACCOUNTING_APPROVAL);
        accountingTasks.add(TaskType.CLIENT_APPROVAL);
        taskTypeDictionary.put("accounting", accountingTasks);

        Set<TaskType> stakeholderTasks = new HashSet<>();
        stakeholderTasks.add(TaskType.STATEKHOLDER_APPROVAL);
        taskTypeDictionary.put("stakeholder", stakeholderTasks);
    }
}
