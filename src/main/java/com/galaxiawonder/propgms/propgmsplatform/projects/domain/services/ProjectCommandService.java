package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectCommand;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public interface ProjectCommandService {
    /**
     * Handles the creation of a new {@link Project} based on the provided {@link CreateProjectCommand}.
     * <p>
     * This method validates the input data, creates the project entity,
     * and persists it within the system. If the creation process fails due
     * to business rules or missing dependencies (e.g., organization not found),
     * an empty {@link Optional} is returned or an exception may be thrown.
     *
     * @param command the {@link CreateProjectCommand} containing the required project details
     * @return an {@link Optional} containing the created {@link Project}, or empty if creation was not successful
     *
     * @throws IllegalArgumentException if any input in the command violates domain rules
     * @throws EntityNotFoundException if the organization or contracting entity is not found
     *
     * @since 1.0
     */
    Optional<Project> handle(CreateProjectCommand command);
}
