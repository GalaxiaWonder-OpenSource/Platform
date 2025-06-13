package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources;

import java.util.Date;

/**
 * Resource representing the details of an organization invitation.
 * <p>
 * This DTO is used to return structured information about an invitation,
 * typically in response to a successful invite operation.
 *
 * @param id               the unique identifier of the invitation
 * @param organizationName the name of the organization issuing the invitation
 * @param invitedBy        the name or email of the person who sent the invitation
 * @param status           the current status of the invitation (e.g., PENDING, ACCEPTED, REJECTED)
 */
public record OrganizationInvitationResource(
        Long id,
        String organizationName,
        String invitedBy,
        String status,
        Date invitedAt
) {
}
