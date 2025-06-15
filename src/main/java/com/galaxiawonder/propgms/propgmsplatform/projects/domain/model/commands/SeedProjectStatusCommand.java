package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;

/**
 * Command used to seed the initial set of project statuses in the system.
 *
 * This command is typically executed during system initialization or migration
 * to ensure all predefined {@link ProjectStatuses} are available in the database.
 *
 * @see ProjectStatuses
 */
public record SeedProjectStatusCommand() {
}
