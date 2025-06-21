package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationMemberTypeCommand;

/**
 * OrganizationMemberTypeCommandService
 *
 * @summary
 * Interface defining command operations related to organization member types.
 * It includes functionality for seeding default member types into the system,
 * such as {@code CONTRACTOR} and {@code WORKER}.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface OrganizationMemberTypeCommandService {

    /**
     * Handles the seeding of default organization member types.
     *
     * @param command the command to trigger the seeding process
     * @since 1.0
     */
    void handle(SeedOrganizationMemberTypeCommand command);
}

