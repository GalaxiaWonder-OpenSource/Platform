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
     * {@inheritDoc}
     */
    @Override
    public String generateToken(UserAccount userAccount) {
        return bearerTokenService.generateToken(
                userAccount.getUserName().username(),
                userAccount.getPersonId().personId().toString()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsernameFromToken(String token) {
        return bearerTokenService.getUsernameFromToken(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPersonIdFromToken(String token) {
        return bearerTokenService.getPersonIdFromToken(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateToken(String token) {
        return bearerTokenService.validateToken(token);
    }
}