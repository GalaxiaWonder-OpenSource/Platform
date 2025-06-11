package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources;

/**
 * SignInResponseResource
 *
 * @summary
 * Data transfer object (DTO) representing the response to a successful sign-in request.
 * It contains the authenticated user's public data and a JWT token for session management.
 *
 * @param user  the resource representation of the authenticated user
 * @param token the JWT token issued upon successful authentication
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record SignInResponseResource(
        UserAccountResource user,
        String token
) {}

