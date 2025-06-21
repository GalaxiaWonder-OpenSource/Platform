package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * MilestoneItemName Value Object
 *
 * @summary
 * Represents the projectName of a milestone item (such as a task or a meeting).
 * Ensures the projectName is not null, not blank, and does not exceed 100 characters.
 *
 * Internally wraps a {@code String} description.
 *
 * @param milestoneItemName the textual projectName of the milestone item
 *
 * @since 1.0
 */
@Embeddable
public record MilestoneItemName(String milestoneItemName) {

    /**
     * Default constructor for JPA and deserialization frameworks.
     *
     * @since 1.0
     */
    public MilestoneItemName() {
        this(null);
    }

    /**
     * Constructs a new {@link MilestoneItemName} after validating the input.
     *
     * @param milestoneItemName the projectName to be validated
     * @throws IllegalArgumentException if the description is null, blank, or exceeds 100 characters
     *
     * @since 1.0
     */
    public MilestoneItemName {
        if (milestoneItemName == null || milestoneItemName.isBlank()) {
            throw new IllegalArgumentException("Milestone item projectName must not be null or blank");
        }
        if (milestoneItemName.length() > 100) {
            throw new IllegalArgumentException("Milestone item projectName must not exceed 100 characters");
        }
    }
}
