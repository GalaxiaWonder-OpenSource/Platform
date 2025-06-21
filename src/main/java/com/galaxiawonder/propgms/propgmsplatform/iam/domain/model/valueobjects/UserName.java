package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * UserName Value Object
 *
 * @summary
 * Represents a system username used for authentication and identification.
 * Enforces format and length constraints to ensure consistency and prevent conflicts.
 *
 * Internally wraps a {@code String} description.
 *
 * @param username the string representation of the username
 *
 * @since 1.0
 */
@Embeddable
public record UserName(String username) {

    /**
     * Default constructor for JPA and deserialization frameworks.
     *
     * @since 1.0
     */
    public UserName() {
        this(null);
    }

    /**
     * Constructs a new {@link UserName} instance after validating the provided description.
     *
     * @param username the username string to be validated
     * @throws IllegalArgumentException if the username is null, blank, or violates length constraints
     *
     * @since 1.0
     */
    public UserName {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be null or blank");
        }
        if (username.length() < 4 || username.length() > 30) {
            throw new IllegalArgumentException("Username must be between 4 and 30 characters long");
        }
    }
}
