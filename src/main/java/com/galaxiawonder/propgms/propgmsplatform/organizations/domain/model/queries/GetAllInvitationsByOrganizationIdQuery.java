package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;

/**
 * Query object used to retrieve all {@link OrganizationInvitation} entities
 * associated with a specific {@link Organization}, identified by its unique ID.
 *
 * <p>
 * This query is typically handled by a query service that returns all invitations
 * belonging to the specified organization, possibly enriched with additional metadata.
 * </p>
 *
 * @param organizationId the unique identifier of the organization whose invitations are being requested
 *
 * @since 1.0
 */
public record GetAllInvitationsByOrganizationIdQuery(
        Long organizationId
) {
}
