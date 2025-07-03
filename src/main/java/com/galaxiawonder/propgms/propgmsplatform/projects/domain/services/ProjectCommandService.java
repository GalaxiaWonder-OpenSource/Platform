package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.DeleteOrganizationMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;
/**
 * @name ProjectCommandService
 * @summary
 * This interface represents the service to handle project source commands.
 */
public interface ProjectCommandService {
    /**
     * Handles the create project command.
     * @param command The create project command containing the required project details.
     * @return The created project.
     *
     * @throws IllegalArgumentException If any required field in the command is null or blank.
     * @throws EntityNotFoundException If the associated organization or contracting entity is not found.
     * @see CreateProjectCommand
     */
    Optional<Project> handle(CreateProjectCommand command);
    /**
     * Handles the update project command.
     * @param command The update project command containing the updated project data.
     * @return The updated project.
     * @see UpdateProjectCommand
     */
    Optional<Project> handle(UpdateProjectCommand command);
    /**
     * Handles the delete project command.
     * @param command The delete project command containing the project ID.
     * @see DeleteProjectCommand
     */
    void handle(DeleteProjectCommand command);
    /**
     * Handles the create project team member command.
     * @param command The create project team member command containing the organization member ID and project ID.
     * @return The created project team member.
     *
     * @throws IllegalArgumentException If any required field in the command is null.
     * @throws EntityNotFoundException If the associated project or organization member is not found.
     * @see CreateProjectTeamMemberCommand
     */
    Optional<ProjectTeamMember> handle(CreateProjectTeamMemberCommand command);
    /**
     * Handles the command to delete an organization member by their unique ID.
     *
     * <p>
     * This method performs validation and removes the corresponding {@link OrganizationMember}
     * entity from the system. It may also trigger additional domain events or cleanup operations
     * depending on business rules.
     * </p>
     *
     * @param command the {@link DeleteProjectTeamMemberCommand} containing the ID of the team member to delete
     * @throws IllegalArgumentException if the member does not exist or cannot be deleted due to business constraints
     *
     * @since 1.0
     */
    void handle(DeleteProjectTeamMemberCommand command);
}
