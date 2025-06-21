package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.TaskStatuses;

/**
 * Command used to seed the initial set of task statuses in the system.
 *
 * This command is typically executed during system initialization or migration
 * to ensure all predefined {@link TaskStatuses} values are available in the database.
 *
 * @see TaskStatuses
 *
 * @since 1.0
 */
public record SeedTaskStatusCommand() {
}