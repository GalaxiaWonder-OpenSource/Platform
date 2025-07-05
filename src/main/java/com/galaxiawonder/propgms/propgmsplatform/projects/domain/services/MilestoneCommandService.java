package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateMilestoneCommand;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MilestoneCommandService {
    /**
     * Handles the create milestone command
     * @param command The create milestone command containing the required milestone details.
     * @return The created milestone
     */
    Optional<Milestone> handle(CreateMilestoneCommand command);
}
