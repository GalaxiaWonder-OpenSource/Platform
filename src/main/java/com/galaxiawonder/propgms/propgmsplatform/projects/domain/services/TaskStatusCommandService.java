package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedTaskStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.TaskStatuses;

/**
 * TaskStatusCommandService
 *
 * @summary
 * Interface that defines command operations related to task statuses,
 * such as seeding the default values based on the {@link TaskStatuses} enum.
 *
 * This service is typically invoked during application startup or migration
 * to ensure all task-related status values are available in the system.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface TaskStatusCommandService {

    /**
     * Handles the seeding of all task status values into the database.
     *
     * @param command the command to trigger seeding
     */
    void handle(SeedTaskStatusCommand command);
}
