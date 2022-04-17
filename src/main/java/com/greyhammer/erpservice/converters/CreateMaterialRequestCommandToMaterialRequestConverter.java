package com.greyhammer.erpservice.converters;

import com.greyhammer.erpservice.commands.CreateMaterialRequestCommand;
import com.greyhammer.erpservice.models.*;
import com.greyhammer.erpservice.services.ScopeOfWorkService;
import com.greyhammer.erpservice.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class CreateMaterialRequestCommandToMaterialRequestConverter
        implements Converter<CreateMaterialRequestCommand, MaterialRequest> {

    private final ScopeOfWorkService scopeOfWorkService;

    public CreateMaterialRequestCommandToMaterialRequestConverter(ScopeOfWorkService scopeOfWorkService) {
        this.scopeOfWorkService = scopeOfWorkService;
    }

    @Override
    public MaterialRequest convert(CreateMaterialRequestCommand source) {
        try {
            ScopeOfWorkTask task = scopeOfWorkService.getTaskById(source.getTaskId());
            MaterialRequest request = new MaterialRequest();
            request.setTask(task);
            request.setDate(new Date());
            request.setStatus(MaterialRequestStatus.PENDING);
            request.setRequestBy(UserSessionUtil.getCurrentUsername());

            Set<MaterialRequestItem> items = new HashSet<>();

            for(CreateMaterialRequestCommand.CreateMaterialRequestItemCommand command : source.getItems()) {
                ScopeOfWorkMaterial material = scopeOfWorkService.getMaterialById(command.getMaterialId());
                MaterialRequestItem item = new MaterialRequestItem();
                item.setMaterial(material);
                item.setRequest(request);
                item.setQty(command.getQty());
                items.add(item);
            }

            request.setItems(items);
            return request;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
