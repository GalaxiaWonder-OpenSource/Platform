package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.acl.OrganizationContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.*;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.events.ProjectCreatedEvent;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectTeamMemberCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ProjectCommandService Implementation
 *  *
 *  * @summary
 *  * Implementation of the ProjectCommandService interface.
 *  * It is responsible for handling project commands.
 */
@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {
    /** Repository interface for performing CRUD operations on {@link Project} entities. */
    private final ProjectRepository projectRepository;

    /** Facade for accessing IAM-related information such as user profiles and identities. */
    private final IAMContextFacade iamContextFacade;

    /** Repository interface for managing and retrieving {@link ProjectStatus} entities. */
    private final ProjectStatusRepository projectStatusRepository;

    /** Publisher used to dispatch domain events such as {@link ProjectCreatedEvent}. */
    private final ProjectTeamMemberCommandService projectTeamMemberCommandService;

    /** Facade for accessing organization context and organization member information. */
    private final OrganizationContextFacade organizationContextFacade;

    /**
     * Constructs the service implementation with required dependencies.
     *
     * @param projectRepository repository for persisting and retrieving projects
     * @param iamContextFacade facade for accessing identity and profile data
     * @param projectStatusRepository repository for accessing project status definitions
     * @param projectTeamMemberCommandService publisher for propagating domain events to the application context
     */
    public ProjectCommandServiceImpl(ProjectRepository projectRepository,
                                     IAMContextFacade iamContextFacade,
                                     ProjectStatusRepository projectStatusRepository,
                                     ProjectTeamMemberCommandService projectTeamMemberCommandService,
                                     OrganizationContextFacade organizationContextFacade) {
        this.projectRepository = projectRepository;
        this.iamContextFacade = iamContextFacade;
        this.projectStatusRepository = projectStatusRepository;
        this.projectTeamMemberCommandService = projectTeamMemberCommandService;
        this.organizationContextFacade = organizationContextFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Project> handle(CreateProjectCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("CreateProjectCommand must not be null");
        }

        Long contractingEntityId = iamContextFacade.getPersonIdFromEmail(command.contractingEntityEmail());
        ProfileDetails details = iamContextFacade.getProfileDetailsById(contractingEntityId);

        ProjectStatus initialStatus = getProjectStatus(ProjectStatuses.BASIC_STUDIES);

        var project = new Project(
                command,
                initialStatus,
                new PersonId(contractingEntityId),
                details.name(),
                details.email()
        );

        var createdProject = projectRepository.save(project);

        var contractorPersonId = organizationContextFacade.getContractorIdFromOrganizationId(command.organizationId());

        var contractorOrganizationMemberId = organizationContextFacade.getOrganizationMemberIdFromPersonAndOrganizationId(contractorPersonId, command.organizationId());

        var specialty = iamContextFacade.getSpecialtyFromPersonId(contractorPersonId);

        projectTeamMemberCommandService.handle(
                new CreateProjectTeamMemberCommand(
                        contractorOrganizationMemberId,
                        createdProject.getId(),
                        contractorPersonId,
                        specialty.getStringName(),
                        "COORDINATOR"
                )
        );

        return Optional.of(createdProject);
    }

    /**
     * Retrieves the {@link ProjectStatus} entity matching the given enum description.
     *
     * @param status the {@link ProjectStatuses} enum representing the desired status
     * @return the corresponding {@link ProjectStatus} entity
     * @throws IllegalStateException if the status is not found in the repository
     */
    private ProjectStatus getProjectStatus(ProjectStatuses status) {
        return this.projectStatusRepository.findByName(status)
                .orElseThrow(() -> new IllegalStateException("Project status not found"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(DeleteProjectCommand command) {
        var project = projectRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + command.id()));

        projectRepository.delete(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Project> handle(UpdateProjectCommand command) {
        var result = projectRepository.findById(command.projectId());
        if (result.isEmpty()) throw new IllegalArgumentException("Project doesn't exist");

        var projectToUpdate = result.get();

        ProjectStatus newStatus = projectToUpdate.getStatus();

        if (!command.status().isBlank()) {
            ProjectStatuses statusEnum = ProjectStatuses.valueOf(command.status().toUpperCase());
            newStatus = getProjectStatus(statusEnum);
        }

        projectToUpdate.updateInformation(
                command.name(),
                command.description(),
                newStatus,
                command.endingDate()
        );

        projectRepository.save(projectToUpdate);

        return Optional.of(projectToUpdate);
    }
}
