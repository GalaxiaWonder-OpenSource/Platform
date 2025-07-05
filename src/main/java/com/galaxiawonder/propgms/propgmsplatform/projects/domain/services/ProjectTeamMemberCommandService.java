package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectTeamMemberCommand;

import java.util.Optional;

public interface ProjectTeamMemberCommandService {
    /**
     * Handles the creation of a new {@link ProjectTeamMember} based on the provided command.
     *
     * @param command the command containing necessary data to create the team member
     * @return an {@link Optional} containing the created {@link ProjectTeamMember} if successful
     */
    Optional<ProjectTeamMember> handle(CreateProjectTeamMemberCommand command);
}
