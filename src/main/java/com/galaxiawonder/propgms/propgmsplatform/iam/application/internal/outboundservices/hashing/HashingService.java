package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.outboundservices.hashing;

/**
 * HashingService
 *
 * @summary
 * Interface that defines operations for secure password hashing and verification.
 * Allows encoding plain text passwords and validating them against stored hashes.
 *
 * Implementations can use various algorithms such as BCrypt, SCrypt, or Argon2.
 *
 * @since 1.0
 */
public interface HashingService {

    /**
     * Encodes a raw password into a secure, hashed representation.
     *
     * @param rawPassword the plain text password to encode
     * @return a hashed version of the input password
     * @since 1.0
     */
    String encode(String rawPassword);

    /**
     * Verifies whether the given raw password matches the encoded hash.
     *
     * @param rawPassword the plain text password provided by the user
     * @param encodedPassword the stored hashed password for comparison
     * @return true if the raw password matches the hash; false otherwise
     * @since 1.0
     */
    boolean matches(String rawPassword, String encodedPassword);
}
