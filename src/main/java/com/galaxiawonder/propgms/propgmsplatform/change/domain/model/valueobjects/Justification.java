package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Justification Value Object
 *
 * @summary
 * Represents a change process justification, change order description and change response notes.
 * Ensures that the justification is not null, not blank, and does not exceed 100 characters.
 * Internally wraps a {@code String} justification.
 *
 * @param justification the textual content of the justification
 *
 * @since 1.0
 */
@Embeddable
public record Justification(String justification) {

    /**
     * Default constructor for JPA and deserialization frameworks.
     *
     * @since 1.0
     */
    public Justification() {this(null);}

    /**
     * Constructs a new {@link Justification} after validating the input.
     *
     * @param justification the justification string to be validated.
     * @throws IllegalArgumentException if the justification is null, blank, or exceeds 100 characters
     */
    public Justification {
        if (justification == null || justification.isBlank()) {
            throw new IllegalArgumentException("Justification must not be null or blank");
        }
        if (justification.length() > 100) {
            throw new IllegalArgumentException("Justification must not be longer than 100 characters");
        }
    }
}
