package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SeedUserTypeCommand;

/**
 * UserTypeCommandService
 *
 * @summary
 * Interface defining command operations related to user types.
 * It includes functionality for seeding default user types into the system.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface UserTypeCommandService {

    /**
     * Handles the seeding of default user types.
     *
     * @param command the command to trigger the seeding process
     * @since 1.0
     */
    void handle(SeedUserTypeCommand command);
}

