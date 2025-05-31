package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetOrganizationByIdQuery;

import java.util.Optional;

/**
 * @name OrganizationQueryService
 *
 * @summary
 * This interface represents the service to handle organization source queries.
 */
public interface OrganizationQueryService {
    /**
     * Handles the get organization source by id query
     * @param query the get organization source by id query
     * @return the organization source if exists
     * @throws IllegalArgumentException If id is null or empty
     * @see GetOrganizationByIdQuery
     */
    Optional<Organization> handle(GetOrganizationByIdQuery query);
}
