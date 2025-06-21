package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

/**
 * RejectInvitationCommand
 *
 * @summary
 * Command used to reject a pending organization invitation.
 * This action updates the invitation status to {@code REJECTED} and prevents further acceptance.
 *
 * <p>Typically triggered by the invitee when they choose not to join the organization.</p>
 *
 * @param invitationId the unique identifier of the invitation to be rejected
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record RejectInvitationCommand(
        Long invitationId
) {}
