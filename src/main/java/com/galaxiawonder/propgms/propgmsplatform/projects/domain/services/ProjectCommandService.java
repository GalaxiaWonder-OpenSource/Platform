package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.DeleteProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateProjectCommand;
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
    /**
     * Handle an update project command
     * @param command The update project command containing the course data
     * @return The updated course
     * @see UpdateProjectCommand
     */
    Optional<Project> handle(UpdateProjectCommand command);
    /**
     * Handles a delete course command.
     * @param command The delete project command containing the id
     * @see DeleteProjectCommand
     */
    void handle(DeleteProjectCommand command);
}
