package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources;

import jakarta.annotation.Nullable;

import java.util.Date;

/**
 * Resource representing the details of an organization invitation.
 *
 * <p>
 * This DTO is used to return structured information about an invitation,
 * typically in response to operations such as sending, listing, or reviewing invitations.
 * It includes contextual metadata like who invited, when, and the associated organization.
 * </p>
 *
 * <p>
 * Fields marked as {@code @Nullable} may vary depending on the use case,
 * e.g., listing invitations versus viewing full details.
 * </p>
 *
 * @param id               the unique identifier of the invitation
 * @param organizationName the name of the organization issuing the invitation (nullable)
 * @param invitedBy        the name or email of the user who sent the invitation (nullable)
 * @param status           the current status of the invitation (e.g., PENDING, ACCEPTED, REJECTED)
 * @param invitedAt        the timestamp indicating when the invitation was created
 * @param invitedPerson    the name or email of the person being invited (nullable)
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record OrganizationInvitationResource(
        Long id,
        @Nullable String organizationName,
        @Nullable String invitedBy,
        String status,
        Date invitedAt,
        @Nullable String invitedPerson
) {
}
