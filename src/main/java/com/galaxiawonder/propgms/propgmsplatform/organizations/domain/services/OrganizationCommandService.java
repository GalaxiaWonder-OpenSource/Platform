package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.*;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Triple;

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
     * Handles the command to invite a person to an organization using their email address.
     *
     * <p>This method:
     * <ul>
     *   <li>Resolves the {@link PersonId} from the provided email.</li>
     *   <li>Loads the target {@link Organization} by its ID.</li>
     *   <li>Creates and attaches a new {@link OrganizationInvitation} in PENDING status.</li>
     *   <li>Persists the organization, cascading the invitation.</li>
     *   <li>Returns the organization, the newly created invitation, and the inviter's {@link ProfileDetails}.</li>
     * </ul>
     *
     * @param command the {@link InvitePersonToOrganizationByEmailCommand} containing the email and organization ID
     * @return an {@link Optional} containing a {@link Triple} of the organization, the invitation, and profile details
     * @throws EntityNotFoundException  if the organization is not found
     * @throws IllegalArgumentException if the person is already a member or has a pending invitation
     * @since 1.0
     */
    Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(InvitePersonToOrganizationByEmailCommand command);

    /**
     * Handles the command to accept an invitation by its ID.
     *
     * <p>This method performs the following actions:
     * <ul>
     *   <li>Resolves the {@link Organization} aggregate that contains the invitation.</li>
     *   <li>Validates that the invitation exists and is in a {@code PENDING} state.</li>
     *   <li>Marks the invitation as {@code ACCEPTED} using domain logic.</li>
     *   <li>Adds the invited person as a new {@link OrganizationMember}.</li>
     *   <li>Persists the updated {@link Organization} aggregate, cascading the changes.</li>
     *   <li>Returns the updated organization, the accepted invitation, and the inviter's {@link ProfileDetails}.</li>
     * </ul>
     *
     * @param command the {@link AcceptInvitationCommand} containing the invitation ID to accept
     * @return an {@link Optional} containing a {@link Triple} of organization, invitation, and profile details
     * @throws EntityNotFoundException if the invitation or the associated organization is not found
     * @throws IllegalStateException if the invitation is not in a {@code PENDING} state
     * @since 1.0
     */
    Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(AcceptInvitationCommand command);

    /**
     * Handles the command to reject an organization invitation.
     *
     * <p>This method:
     * <ul>
     *   <li>Fetches the {@link Organization} associated with the given invitation ID.</li>
     *   <li>Validates that the invitation exists and is in {@code PENDING} status.</li>
     *   <li>Marks the invitation as {@code REJECTED} using domain logic.</li>
     *   <li>Persists the updated {@link Organization} aggregate.</li>
     *   <li>Returns the updated organization, the rejected invitation, and the inviter's {@link ProfileDetails}.</li>
     * </ul>
     *
     * @param rejectInvitationCommand the {@link RejectInvitationCommand} containing the invitation ID
     * @return an {@link Optional} containing a {@link Triple} of organization, invitation, and profile details
     * @throws EntityNotFoundException if no organization is found for the given invitation ID
     * @throws IllegalStateException if the invitation is not in {@code PENDING} status
     * @since 1.0
     */
    Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(RejectInvitationCommand rejectInvitationCommand);

    /**
     * Handles the command to delete an organization member by their unique ID.
     *
     * <p>
     * This method performs validation and removes the corresponding {@link OrganizationMember}
     * entity from the system. It may also trigger additional domain events or cleanup operations
     * depending on business rules.
     * </p>
     *
     * @param command the {@link DeleteOrganizationMemberCommand} containing the ID of the member to delete
     * @throws IllegalArgumentException if the member does not exist or cannot be deleted due to business constraints
     *
     * @since 1.0
     */
    void handle(DeleteOrganizationMemberCommand command);
}
