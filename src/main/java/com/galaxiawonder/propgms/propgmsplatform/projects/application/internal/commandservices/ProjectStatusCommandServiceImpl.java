package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedProjectStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectStatusCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * ProjectStatusCommandService Implementation
 *
 * @summary
 * Service implementation responsible for handling project status command operations,
 * such as seeding default project statuses into the database if they do not exist.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class ProjectStatusCommandServiceImpl implements ProjectStatusCommandService {

    /** Repository for managing {@link ProjectStatus} entities. */
    private final ProjectStatusRepository projectStatusRepository;

    /**
     * Constructs a new {@code ProjectStatusCommandServiceImpl} with the specified repository.
     *
     * @param projectStatusRepository the repository used to persist {@link ProjectStatus} entities
     */
    public ProjectStatusCommandServiceImpl(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(SeedProjectStatusCommand command) {
        Arrays.stream(ProjectStatuses.values()).forEach(projectStatus -> {
            if (!projectStatusRepository.existsByName(projectStatus)) {
                projectStatusRepository.save(new ProjectStatus(projectStatus));
            }
        });
    }
}
