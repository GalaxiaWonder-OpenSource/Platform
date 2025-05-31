package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.OrganizationSource;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationSourceCommand;

import java.util.Optional;

/**
 * @name OrganizationSourceCommandService
 * @summary
 * This interface represents the service to handle organization source commands.
 */
public interface OrganizationSourceCommandService {
    /**
     * Handles the create organization source command.
     * @param command The create organization source command.
     * @return The created organization source.
     *
     * @throws IllegalArgumentException If legalName, ruc, createdBy or status is null or empty
     * @see CreateOrganizationSourceCommand
     */
    Optional<OrganizationSource> handle(CreateOrganizationSourceCommand command);
}
