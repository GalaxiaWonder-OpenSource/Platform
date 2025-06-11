package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.outboundservices.tokens.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.outboundservices.tokens.TokenService;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.tokens.jwt.BearerTokenService;
import org.springframework.stereotype.Service;

/**
 * TokenServiceImpl
 *
 * @summary
 * Implementation of {@link TokenService} that delegates token operations
 * to the underlying {@link BearerTokenService}.
 * Provides token generation and validation methods for authentication workflows.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class TokenServiceImpl implements TokenService {

    /**
     * Service responsible for low-level bearer token generation and parsing.
     */
    private final BearerTokenService bearerTokenService;

    /**
     * Constructs a new {@code TokenServiceImpl} with the required token utility service.
     *
     * @param bearerTokenService service used for handling JWT token logic
     */
    public TokenServiceImpl(BearerTokenService bearerTokenService) {
        this.bearerTokenService = bearerTokenService;
    }

    /**
     * Generates a JWT token for the given user account.
     *
     * @param userAccount the user account for which to generate the token
     * @return a signed JWT token string
     */
    @Override
    public String generateToken(UserAccount userAccount) {
        return bearerTokenService.generateToken(
                userAccount.getUserName().username(),
                userAccount.getPersonId().personId().toString()
        );
    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token
     * @return the username encoded in the token
     */
    @Override
    public String getUsernameFromToken(String token) {
        return bearerTokenService.getUsernameFromToken(token);
    }

    /**
     * Extracts the person ID from the provided JWT token.
     *
     * @param token the JWT token
     * @return the person ID encoded in the token
     */
    @Override
    public String getPersonIdFromToken(String token) {
        return bearerTokenService.getPersonIdFromToken(token);
    }

    /**
     * Validates whether the provided JWT token is valid and not expired.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid; false otherwise
     */
    @Override
    public boolean validateToken(String token) {
        return bearerTokenService.validateToken(token);
    }
}