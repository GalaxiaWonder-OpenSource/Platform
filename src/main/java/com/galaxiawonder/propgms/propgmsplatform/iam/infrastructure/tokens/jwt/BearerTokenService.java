package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.tokens.jwt;

import com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * BearerTokenService
 * <p>
 *     Interface that defines the methods to generate and extract Bearer Tokens.
 *     This interface extends the TokenService interface.
 * </p>
 */
public interface BearerTokenService {
    /**
     * Method to extract the Bearer Token from a HttpServletRequest.
     * @param token {@link HttpServletRequest} The request from which the token will be extracted.
     * @return String containing the Bearer Token.
     */
    String getBearerTokenFrom(HttpServletRequest token);

    /**
     * Method to generate a Bearer Token from an Authentication object.
     * @param authentication {@link Authentication} The authentication object from which the token will be generated.
     * @return String containing the Bearer Token.
     */
    String generateToken(Authentication authentication);

    String generateToken(String username);

    String generateToken(String username, String personId);

    String getUsernameFromToken(String token);

    String getPersonIdFromToken(String token);

    boolean validateToken(String token);
}
