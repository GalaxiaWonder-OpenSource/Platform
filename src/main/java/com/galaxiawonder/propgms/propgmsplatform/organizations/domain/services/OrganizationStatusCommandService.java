package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationStatusCommand;

/**
 * OrganizationStatusCommandService
 *
 * @summary
 * Interface defining command operations related to organization types.
 * It includes functionality for seeding default organization types into the system.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface OrganizationStatusCommandService {

    /**
     * Handles the seeding of default organization types.
     *
     * @param command the command to trigger the seeding process
     * @since 1.0
     */
    void handle(SeedOrganizationStatusCommand command);
}

