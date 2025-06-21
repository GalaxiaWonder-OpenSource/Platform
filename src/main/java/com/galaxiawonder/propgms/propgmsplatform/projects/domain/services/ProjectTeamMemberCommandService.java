package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectTeamMemberCommand;

import java.util.Optional;

/**
 * Application service interface responsible for handling commands related to {@link ProjectTeamMember} entities.
 *
 * <p>This interface defines the contract for use cases that involve the creation or management of project team members,
 * encapsulating the domain logic required to modify the project team membership state.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface ProjectTeamMemberCommandService {

    /**
     * Handles the creation of a new {@link ProjectTeamMember} based on the provided {@link CreateProjectTeamMemberCommand}.
     *
     * <p>This use case typically involves assigning a person to a project, linking them with their organization membership
     * and area of specialty, and persisting the new team member in the system.</p>
     *
     * @param command the command containing all necessary information to create the project team member
     * @return an {@link Optional} containing the created {@code ProjectTeamMember} if the operation was successful;
     *         otherwise, an empty Optional
     */
    Optional<ProjectTeamMember> handle(CreateProjectTeamMemberCommand command);
}
