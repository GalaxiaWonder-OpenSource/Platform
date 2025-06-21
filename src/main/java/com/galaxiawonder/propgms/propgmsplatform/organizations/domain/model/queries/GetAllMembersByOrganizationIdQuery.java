package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;

/**
 * Query object used to retrieve all {@link OrganizationMember} entities
 * associated with a specific {@link Organization}, identified by its unique ID.
 *
 * <p>
 * This query is typically handled by a service that fetches the list of members
 * currently registered under the specified organization.
 * </p>
 *
 * @param organizationId the unique identifier of the organization whose members are being requested
 *
 * @since 1.0
 */
public record GetAllMembersByOrganizationIdQuery(
        Long organizationId
) {
}
