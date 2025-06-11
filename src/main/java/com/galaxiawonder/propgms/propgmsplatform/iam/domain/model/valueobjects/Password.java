package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import jakarta.persistence.Embeddable;

/**
 * Password
 *
 * @summary
 * Value object representing a user's hashed password.
 * Validates the input to ensure it is non-null and non-blank before being stored.
 * Typically used within {@link UserAccount} for secure credential handling.
 *
 * @param hashedPassword the hashed representation of the user's password
 *
 * @since 1.0
 */
@Embeddable
public record Password(String hashedPassword) {

    /**
     * Default constructor required by JPA.
     * Initializes the password as {@code null}.
     */
    public Password() {
        this(null);
    }

    /**
     * Canonical constructor with validation.
     * Ensures the provided password is not null or blank.
     *
     * @param hashedPassword the hashed password string to be validated and stored
     * @throws IllegalArgumentException if the password is null or blank
     */
    public Password {
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("Password must not be null nor blank.");
        }
    }
}