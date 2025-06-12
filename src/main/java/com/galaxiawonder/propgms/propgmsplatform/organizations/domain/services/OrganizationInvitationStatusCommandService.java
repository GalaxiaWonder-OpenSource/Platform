package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationInvitationStatusCommand;

/**
 * OrganizationInvitationStatusCommandService
 *
 * @summary
 * Interface defining command operations related to organization invitation statuses.
 * Includes functionality for seeding default invitation statuses into the system.
 *
 * <p>This is typically used during application initialization to ensure all expected
 * invitation statuses are present in the database.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface OrganizationInvitationStatusCommandService {

    /**
     * Handles the seeding of default organization invitation statuses.
     *
     * @param command the command that triggers the seeding process
     * @since 1.0
     */
    void handle(SeedOrganizationInvitationStatusCommand command);
}

