package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;

/**
 * ExistsByUserNameQuery
 *
 * @summary
 * Query object used to check whether a {@link UserAccount} exists for a given username.
 * Encapsulates the input required for a read-only existence check, typically used for validation
 * or UI feedback during registration or username updates.
 *
 * @param userName the username to check for existence in the system
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record ExistsByUserNameQuery(String userName) {
}

