package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.UpdateOrganizationCommand;

import java.util.Optional;

/**
 * @name OrganizationCommandService
 * @summary
 * This interface represents the service to handle organization source commands.
 */
public interface OrganizationCommandService {
    /**
     * Handles the create organization source command.
     * @param command The create organization source command.
     * @return The created organization source.
     *
     * @throws IllegalArgumentException If legalName, ruc, createdBy or status is null or empty
     * @see CreateOrganizationCommand
     */
    Optional<Organization> handle(CreateOrganizationCommand command);
    /**
     * Handles a delete course command.
     * @param command The delete organization command containing the ruc
     * @see DeleteOrganizationCommand
     */
    void handle(DeleteOrganizationCommand command);

    /**
     * Handle an update organization command
     * @param command The update organization command containing the course data
     * @return The updated course
     * @see UpdateOrganizationCommand
     */
    Optional<Organization> handle(UpdateOrganizationCommand command);
}
