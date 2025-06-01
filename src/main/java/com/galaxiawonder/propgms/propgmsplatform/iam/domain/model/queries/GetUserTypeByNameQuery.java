package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserTypes;

/**
 * GetUserTypeByNameQuery
 *
 * @summary
 * A query object used to retrieve a specific user type by its name.
 * The user type is represented by an enum value from {@link UserTypes}.
 *
 * @param userType the user type to search for
 *
 * @since 1.0
 */
public record GetUserTypeByNameQuery(UserTypes userType) {
}

