package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import jakarta.annotation.Nullable;

/**
 * SignUpResource
 *
 * @summary
 * Resource used for user account registration requests.
 * Encapsulates all necessary information for creating a new {@link UserAccount},
 * including personal data and account credentials.
 *
 * Used as input in the sign-up process, typically consumed by the {@code /api/v1/auth/signup} endpoint.
 *
 * @param userName the unique identifier to access the system (required)
 * @param password the raw password to be securely hashed before persistence (required)
 * @param userType the role or category of the user (e.g., TYPE_CLIENT, TYPE_WORKER) (required)
 * @param firstName the user's first projectName (required)
 * @param lastName the user's last projectName (required)
 * @param email the user's email address (required)
 * @param phone the user's phone number in E.164 format (optional)
 *
 * @since 1.0
 */
public record SignUpResource(
        String userName,
        String password,
        String userType,
        String firstName,
        String lastName,
        String email,
        @Nullable String phone,
        @Nullable String professionalId,
        @Nullable String specialty
) {
}


