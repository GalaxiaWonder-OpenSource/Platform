package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.InvitePersonToOrganizationByEmailCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.ImmutablePair;

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

    /**
     * Handles the process of inviting a person to an organization using their email address.
     *
     * <p>This command performs the following steps:
     * <ul>
     *   <li>Resolves the {@link PersonId} associated with the provided email.</li>
     *   <li>Fetches the target {@link Organization} by its ID.</li>
     *   <li>Validates that the person is not already a member and has no pending invitation.</li>
     *   <li>Adds a new {@link OrganizationInvitation} with {@code PENDING} status to the organization.</li>
     *   <li>Returns the updated organization along with the profile of the user who issued the invitation.</li>
     * </ul>
     *
     * @param command the command containing the email of the person to invite and the target organization ID
     * @return an {@link Optional} containing a pair of {@link Organization} and {@link ProfileDetails}
     * @throws EntityNotFoundException if the organization does not exist or the person profile cannot be resolved
     * @throws IllegalArgumentException if the person is already a member or already has a pending invitation
     *
     * @since 1.0
     */
    Optional<ImmutablePair<Organization, ProfileDetails>> handle(InvitePersonToOrganizationByEmailCommand command);
}
