package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectTeamMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedProjectTeamMemberTypeCommand;

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
public interface ProjectTeamMemberTypeCommandService {
    /**
     * ???
     */
    void handle(SeedProjectTeamMemberTypeCommand command);
}
