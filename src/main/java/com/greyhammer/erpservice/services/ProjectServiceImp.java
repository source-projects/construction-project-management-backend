package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.converters.CreateProjectCommandToProjectConverter;
import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Customer;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.repositories.ProjectRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CreateProjectCommandToProjectConverter createProjectCommandToProjectConverter;
    private final CustomerService customerService;

    public ProjectServiceImp(
            ProjectRepository projectRepository,
            CreateProjectCommandToProjectConverter createProjectCommandToProjectConverter,
            CustomerService customerService) {
        this.projectRepository = projectRepository;
        this.createProjectCommandToProjectConverter = createProjectCommandToProjectConverter;
        this.customerService = customerService;
    }

    public Set<Project> getAllProjects(Pageable pageable) {
        Set<Project> projects = new HashSet<Project>();
        projectRepository.findAll(pageable).iterator().forEachRemaining(projects::add);
        return projects;
    }

    @Override
    public Project getProjectById(Long id) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty())
            throw new ProjectNotFoundException();

        return project.get();
    }

    @Override
    @Transactional
    public Project handleCreateCommand(CreateProjectCommand command) throws CustomerNotFoundException {
        Project project = createProjectCommandToProjectConverter.convert(command);
        Customer savedCustomer = customerService.getOrCreate(command.getCustomer());
        project.setCustomer(savedCustomer);
        return projectRepository.save(project);
    }
}
