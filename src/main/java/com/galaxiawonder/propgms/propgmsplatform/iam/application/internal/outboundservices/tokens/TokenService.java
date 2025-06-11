package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.outboundservices.tokens;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;

/**
 * TokenService
 *
 * @summary
 * Interface defining operations for managing authentication tokens.
 * Provides methods to generate tokens, extract user information from tokens,
 * and validate their integrity.
 *
 * @since 1.0
 */
public interface TokenService {

    /**
     * Generates a token for the given {@link UserAccount}.
     *
     * @param userAccount the user account for which to generate the token
     * @return the generated token as a {@code String}
     */
    String generateToken(UserAccount userAccount);

    /**
     * Extracts the username from the given token.
     *
     * @param token the token containing user data
     * @return the username encoded in the token
     */
    String getUsernameFromToken(String token);

    /**
     * Extracts the person ID from the given token.
     *
     * @param token the token containing user data
     * @return the person ID encoded in the token
     */
    String getPersonIdFromToken(String token);

    /**
     * Validates the integrity and expiration of a given token.
     *
     * @param token the token to be validated
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    boolean validateToken(String token);
}
