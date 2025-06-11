package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.hashing.bcrypt.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * BCryptHashingService
 *
 * @summary
 * Implements password hashing using the BCrypt algorithm.
 * Provides secure encoding and matching operations for raw passwords.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class BCryptHashingService implements HashingService {

    /** Internal encoder using BCrypt algorithm */
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Encodes a raw password using BCrypt.
     *
     * @param rawPassword the plain text password to hash
     * @return the hashed password string
     *
     * @since 1.0
     */
    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Verifies whether a raw password matches a previously hashed one.
     *
     * @param rawPassword the plain text password to check
     * @param encodedPassword the hashed password to compare against
     * @return true if the raw password matches the encoded one, false otherwise
     *
     * @since 1.0
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

