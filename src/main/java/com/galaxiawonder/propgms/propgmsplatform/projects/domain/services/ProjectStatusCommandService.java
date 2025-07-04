package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedProjectStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;

/**
 * ProjectStatusCommandService
 *
 * @summary
 * Interface that defines command operations related to project statuses,
 * such as seeding the default values based on the {@link ProjectStatuses} enum.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface ProjectStatusCommandService {

    /**
     * Handles the seeding of all project status values into the database.
     *
     * @param command the command to trigger seeding
     */
    void handle(SeedProjectStatusCommand command);
}

