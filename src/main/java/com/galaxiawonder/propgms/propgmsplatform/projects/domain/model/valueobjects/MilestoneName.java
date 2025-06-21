package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * MilestoneName
 *
 * @summary
 * Value object that represents the projectName of a milestone within a project schedule.
 * Ensures the projectName is not null, not blank, and does not exceed 100 characters.
 *
 * Internally wraps a {@code String} description to provide type safety and domain expressiveness.
 *
 * @param milestoneName the textual projectName of the milestone
 *
 * @since 1.0
 */
@Embeddable
public record MilestoneName(String milestoneName) {

    /**
     * Default constructor for JPA and deserialization frameworks.
     */
    public MilestoneName() {
        this(null);
    }

    /**
     * Constructs a new {@link MilestoneName} instance after validating the input.
     *
     * @param milestoneName the milestone projectName to validate
     * @throws IllegalArgumentException if the description is null, blank, or exceeds 100 characters
     */
    public MilestoneName {
        if (milestoneName == null || milestoneName.isBlank()) {
            throw new IllegalArgumentException("Milestone projectName must not be null or blank");
        }
        if (milestoneName.length() > 20) {
            throw new IllegalArgumentException("Milestone projectName must not exceed 100 characters");
        }
    }
}
