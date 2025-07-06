package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateMilestoneCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.DeleteMilestoneCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateMilestoneCommand;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MilestoneCommandService {
    /**
     * Handles the create milestone command
     * @param command The create milestone command containing the required milestone details.
     * @return The created milestone
     */
    Optional<Milestone> handle(CreateMilestoneCommand command);

    /**
     * Handles the update milestone command
     * @param command The update milestone command contains the milestone id,
     *                name optional, description optional, and date range optional
     * @return The updated milestone
     */
    Optional<Milestone> handle(UpdateMilestoneCommand command);

    /**
     * Handles the delete milestone
     * @param command The delete command contains the milestone Id.
     */
    void handle(DeleteMilestoneCommand command);
}
