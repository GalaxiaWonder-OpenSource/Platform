package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Description Value Object
 *
 * @summary
 * Represents a generic textual description used across different domain entities.
 * Ensures that the description is not null, not blank, and does not exceed 200 characters.
 *
 * Internally wraps a {@code String} description.
 *
 * @param description the textual content of the description
 *
 * @since 1.0
 */
@Embeddable
public record Description(String description) {

    /**
     * Default constructor for JPA and deserialization frameworks.
     *
     * @since 1.0
     */
    public Description() {
        this(null);
    }

    /**
     * Constructs a new {@link Description} after validating the input.
     *
     * @param description the description string to be validated
     * @throws IllegalArgumentException if the description is null, blank, or exceeds 200 characters
     *
     * @since 1.0
     */
    public Description {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description must not be null or blank");
        }
        if (description.length() > 200) {
            throw new IllegalArgumentException("Description must not exceed 200 characters");
        }
    }
}
