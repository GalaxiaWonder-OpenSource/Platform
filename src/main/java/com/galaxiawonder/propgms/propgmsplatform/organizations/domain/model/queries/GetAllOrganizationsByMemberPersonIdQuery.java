package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;

/**
 * Query object used to retrieve all {@link Organization} entities
 * in which a specific person is registered as a member.
 *
 * <p>
 * This query is typically handled by a query service that searches through
 * all {@link OrganizationMember} associations to return the list of organizations
 * linked to the given person ID.
 * </p>
 *
 * @param personId the unique identifier of the person whose associated organizations are being requested
 *
 * @since 1.0
 */
public record GetAllOrganizationsByMemberPersonIdQuery(
        Long personId
) {
}
