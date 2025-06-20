package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectName;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {
    /** Repository interface for performing CRUD operations on {@link Project} entities. */
    private final ProjectRepository projectRepository;

    /** Facade for accessing IAM-related information such as user profiles and identities. */
    private final IAMContextFacade iamContextFacade;

    /** Repository interface for managing and retrieving {@link ProjectStatus} entities. */
    private final ProjectStatusRepository projectStatusRepository;

    /**
     * Constructs the service implementation with required dependencies.
     *
     * @param projectRepository repository for persisting and retrieving projects
     * @param iamContextFacade facade for accessing identity and profile data
     * @param projectStatusRepository repository for accessing project status definitions
     */
    public ProjectCommandServiceImpl(ProjectRepository projectRepository,
                                     IAMContextFacade iamContextFacade,
                                     ProjectStatusRepository projectStatusRepository) {
        this.projectRepository = projectRepository;
        this.iamContextFacade = iamContextFacade;
        this.projectStatusRepository = projectStatusRepository;
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

        return Optional.of(project);
    }

    private ProjectStatus getProjectStatus(ProjectStatuses status) {
        return this.projectStatusRepository.findByName(status)
                .orElseThrow(() -> new IllegalStateException("Project status not found"));
    }
}
