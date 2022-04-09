package com.greyhammer.erpservice.converters;

import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.ProjectDesignStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateProjectCommandToProjectConverter implements Converter<CreateProjectCommand, Project> {
    @Override
    public Project convert(CreateProjectCommand source) {
        final Project project = new Project();
        project.setName(source.getName());
        project.setDescription(source.getDescription());
        project.setHasExistingDesign(source.getHasExistingDesign());

        if (source.getHasExistingDesign()) {
            project.setDesignStatus(ProjectDesignStatus.FINAL);
        } else {
            project.setDesignStatus(ProjectDesignStatus.PENDING);
        }

        return project;
    }
}
