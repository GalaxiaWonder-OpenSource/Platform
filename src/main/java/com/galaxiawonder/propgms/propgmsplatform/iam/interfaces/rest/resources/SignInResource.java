package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources;

/**
 * SignInResource
 *
 * @summary
 * Data transfer object (DTO) used to capture sign-in credentials from the client.
 * Contains the username and password fields required for authentication.
 *
 * @param userName the unique username identifying the user (required)
 * @param password the plain text password for login verification (required)
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record SignInResource(
        String userName,
        String password
) {
}

