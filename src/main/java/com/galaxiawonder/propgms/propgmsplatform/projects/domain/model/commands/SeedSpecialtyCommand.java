package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Specialties;

/**
 * Command used to seed the initial set of project specialities in the system.
 *
 * This command is typically executed during system initialization or migration
 * to ensure all predefined {@link Specialties} are available in the database.
 *
 * @see Specialties
 *
 * @since 1.0
 */
public record SeedSpecialtyCommand() {
}
