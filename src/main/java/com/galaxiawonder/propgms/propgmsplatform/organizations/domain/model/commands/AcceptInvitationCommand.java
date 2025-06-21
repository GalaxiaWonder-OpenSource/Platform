package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

/**
 * AcceptInvitationCommand
 *
 * @summary
 * Command used to accept a pending organization invitation.
 * This action updates the invitation status to {@code ACCEPTED} and typically triggers
 * the creation of a new organization member entry.
 *
 * <p>Usually invoked by the invitee when they agree to join the organization.</p>
 *
 * @param invitationId the unique identifier of the invitation to be accepted
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record AcceptInvitationCommand(
        Long invitationId
) {}