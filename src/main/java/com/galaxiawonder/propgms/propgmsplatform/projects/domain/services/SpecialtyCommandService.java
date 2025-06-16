package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedSpecialtyCommand;

/**
 * SpecialtyCommandService
 *
 * @summary
 * Interface that defines command operations related to project specialties,
 * such as seeding the default values based on the {@link com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Specialties} enum.
 *
 * This service is typically invoked during application startup or migration
 * to ensure all specialty values are available in the system.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface SpecialtyCommandService {

    /**
     * Handles the seeding of all project specialty values into the database.
     *
     * @param command the command to trigger seeding
     */
    void handle(SeedSpecialtyCommand command);
}
