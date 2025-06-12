package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;

/**
 * UserAccountResource
 *
 * @summary
 * Represents a simplified view of a {@link UserAccount} returned after account creation or authentication.
 * Contains only essential user data exposed to the client, excluding sensitive fields like passwords.
 *
 * @param userName the unique username used for identification
 * @param userType the category or role of the user (e.g., TYPE_CLIENT, TYPE_WORKER)
 *
 * @since 1.0
 */
public record UserAccountResource(
        String userName,
        String userType,
        Long personId
) {
}

