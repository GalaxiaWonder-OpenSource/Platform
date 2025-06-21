package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.events.ProjectCreatedEvent;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectName;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation that handles command operations for {@link Project} entities,
 * such as creating and updating project records in the system.
 *
 * <p>This service encapsulates the logic needed to persist new projects,
 * retrieve project statuses, and publish related domain events.</p>
 *
 * <p>It uses the {@link IAMContextFacade} to retrieve identity-related data,
 * and publishes events via {@link ApplicationEventPublisher} when necessary.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
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
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructs the service implementation with required dependencies.
     *
     * @param projectRepository repository for persisting and retrieving projects
     * @param iamContextFacade facade for accessing identity and profile data
     * @param projectStatusRepository repository for accessing project status definitions
     * @param eventPublisher publisher for propagating domain events to the application context
     */
    public ProjectCommandServiceImpl(ProjectRepository projectRepository,
                                     IAMContextFacade iamContextFacade,
                                     ProjectStatusRepository projectStatusRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.projectRepository = projectRepository;
        this.iamContextFacade = iamContextFacade;
        this.projectStatusRepository = projectStatusRepository;
        this.eventPublisher = eventPublisher;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Project> handle(CreateProjectCommand command) {
        Long contractingEntityId = this.iamContextFacade.getPersonIdFromEmail(command.contractingEntityEmail());

        ProjectStatus basicStudiesStatus = getProjectStatus(ProjectStatuses.BASIC_STUDIES);

        Project project = new Project(
                new ProjectName(command.projectName()),
                new Description(command.description()),
                basicStudiesStatus,
                new DateRange(command.startDate(), command.endDate()),
                new OrganizationId(command.organizationId()),
                new PersonId(contractingEntityId)
        );

        this.projectRepository.save(project);

        // The direct publishing of the event done by calling the publish event method
        // from the entity didn't seem to work.
        // Temporarily solved by using ApplicationEventPublisher injected on constructor
        // project.projectCreated();

        eventPublisher.publishEvent(new ProjectCreatedEvent(
                this,
                project.getOrganizationId(),
                new ProjectId(project.getId())
        ));

        return Optional.of(project);
    }

    private ProjectStatus getProjectStatus(ProjectStatuses status) {
        return this.projectStatusRepository.findByName(status)
                .orElseThrow(() -> new IllegalStateException("Project status not found"));
    }
}
