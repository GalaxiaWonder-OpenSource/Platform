package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllUserTypesQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetUserTypeByNameQuery;

import java.util.List;
import java.util.Optional;

/**
 * UserTypeQueryService
 *
 * @summary
 * Interface defining query operations for retrieving user type data.
 * Supports queries for fetching all user types and searching by name.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface UserTypeQueryService {

    /**
     * Handles the query to retrieve all user types.
     *
     * @param query the query to fetch all user types
     * @return a list of {@link UserType} entities
     * @since 1.0
     */
    List<UserType> handle(GetAllUserTypesQuery query);

    /**
     * Handles the query to retrieve a user type by name.
     *
     * @param query the query containing the user type to search for
     * @return an {@link Optional} containing the {@link UserType} if found, otherwise empty
     * @since 1.0
     */
    Optional<UserType> handle(GetUserTypeByNameQuery query);
}

