package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.OrganizationSource;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetOrganizationSourceByIdQuery;

import java.util.Optional;

/**
 * @name OrganizationSourceQueryService
 *
 * @summary
 * This interface represents the service to handle organization source queries.
 */
public interface OrganizationSourceQueryService {
    /**
     * Handles the get organization source by id query
     * @param query the get organization source by id query
     * @return the organization source if exists
     * @throws IllegalArgumentException If id is null or empty
     * @see GetOrganizationSourceByIdQuery
     */
    Optional<OrganizationSource> handle(GetOrganizationSourceByIdQuery query);
}
