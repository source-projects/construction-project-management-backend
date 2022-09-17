package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.CreateProjectCommand;
import com.greyhammer.erpservice.commands.SetProjectDesignStatusCommand;
import com.greyhammer.erpservice.converters.CreateProjectCommandToProjectConverter;
import com.greyhammer.erpservice.events.CreateProjectEvent;
import com.greyhammer.erpservice.exceptions.CustomerNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectInvalidStateException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.models.Customer;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.models.ProjectStatus;
import com.greyhammer.erpservice.repositories.ProjectRepository;
import com.greyhammer.erpservice.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CreateProjectCommandToProjectConverter createProjectCommandToProjectConverter;
    private final CustomerService customerService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProjectServiceImp(
            ProjectRepository projectRepository,
            CreateProjectCommandToProjectConverter createProjectCommandToProjectConverter,
            CustomerService customerService,
            ApplicationEventPublisher applicationEventPublisher) {
        this.projectRepository = projectRepository;
        this.createProjectCommandToProjectConverter = createProjectCommandToProjectConverter;
        this.customerService = customerService;
        this.applicationEventPublisher =applicationEventPublisher;
    }

    public Set<Project> getAll(Pageable pageable) {
        Set<Project> projects = new HashSet<>();

        Page<Project> result = projectRepository.findAll(pageable);

        if (result != null) {
            result.iterator().forEachRemaining(projects::add);
        }

        return projects;
    }

    @Override
    public Project get(Long id) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty())
            throw new ProjectNotFoundException();

        return project.get();
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void approveAsAccounting(Long id) throws ProjectNotFoundException {
        Project project = get(id);
        project.setAccountingApprover(UserSessionUtil.getCurrentUsername());
        project.setAccountingRejector(null);
        projectRepository.save(project);
    }

    @Override
    public void rejectAsAccounting(Long id) throws ProjectNotFoundException {
        Project project = get(id);
        project.setAccountingApprover(null);
        project.setAccountingRejector(UserSessionUtil.getCurrentUsername());
        projectRepository.save(project);
    }

    @Override
    public void approveAsStakeholder(Long id, Double profit) throws ProjectNotFoundException {
        Project project = get(id);
        project.setStakeholderApprover(UserSessionUtil.getCurrentUsername());
        project.setStakeholderRejector(null);
        project.setProfit(profit);
        projectRepository.save(project);
    }

    @Override
    public void rejectAsStakeholder(Long id) throws ProjectNotFoundException {
        Project project = get(id);
        project.setStakeholderApprover(null);
        project.setStakeholderRejector(UserSessionUtil.getCurrentUsername());
        project.setProfit(null);
        projectRepository.save(project);
    }

    @Override
    public void approveAsClient(Long id) throws ProjectNotFoundException {
        Project project = get(id);
        project.setClientApprover(UserSessionUtil.getCurrentUsername());
        project.setClientRejector(null);
        projectRepository.save(project);
    }

    @Override
    public void rejectAsClient(Long id) throws ProjectNotFoundException {
        Project project = get(id);
        project.setClientApprover(null);
        project.setClientRejector(UserSessionUtil.getCurrentUsername());
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project handleCreateCommand(CreateProjectCommand command) throws CustomerNotFoundException {
        Project project = createProjectCommandToProjectConverter.convert(command);
        Customer savedCustomer = customerService.getOrCreate(command.getCustomer());

        if (project != null) {
            project.setCustomer(savedCustomer);
        }
        return createProject(project);
    }

    @Override
    @Transactional
    public Project handleSetDesignStatusCommand(SetProjectDesignStatusCommand command) throws ProjectNotFoundException, ProjectInvalidStateException {
        Project project = get(command.getProjectId());

        if (project.getStatus() != ProjectStatus.DESIGN) {
            throw new ProjectInvalidStateException();
        }

        project.setDesignStatus(command.getStatus());
        return projectRepository.save(project);
    }

    @Override
    public long getTotalProjectCount() {
        return projectRepository.count();
    }

    private Project createProject(Project project) {
        log.debug("Saving new project to repository..");
        projectRepository.save(project);

        log.debug("Successfully saved project repository..");
        CreateProjectEvent createProjectEvent = new CreateProjectEvent(this);
        createProjectEvent.setProject(project);

        log.debug("Dispatching Create Project event.");
        applicationEventPublisher.publishEvent(createProjectEvent);
        return project;
    }
}
