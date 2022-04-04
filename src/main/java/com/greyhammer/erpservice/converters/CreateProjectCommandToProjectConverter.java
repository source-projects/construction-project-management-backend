package com.greyhammer.erpservice.converters;

import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.models.Project;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateProjectCommandToProjectConverter implements Converter<CreateProjectCommand, Project> {
    @Override
    public Project convert(CreateProjectCommand source) {
        if (source == null) {
            return null;
        }

        final Project project = new Project();
        project.setName(source.getName());
        project.setDescription(source.getDescription());
        project.setCustomer(source.getCustomer());
        project.setHasExistingDesign(source.getHasExistingDesign());
        return project;
    }
}
