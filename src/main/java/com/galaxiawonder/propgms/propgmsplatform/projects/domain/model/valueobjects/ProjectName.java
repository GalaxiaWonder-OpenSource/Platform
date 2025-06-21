package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * ProjectName Value Object
 *
 * @summary
 * Represents the projectName of a project in the system.
 * Ensures that the projectName is non-null, non-blank, and within a valid length range.
 *
 * Internally wraps a {@code String} description.
 *
 * @param projectName the string representation of the project projectName
 *
 * @since 1.0
 */
@Embeddable
public record ProjectName(String projectName) {

    /**
     * Default constructor for JPA and deserialization frameworks.
     *
     * @since 1.0
     */
    public ProjectName() {
        this(null);
    }

    /**
     * Constructs a new {@link ProjectName} instance after validating the provided description.
     *
     * @param projectName the project projectName to be validated
     * @throws IllegalArgumentException if the projectName is null, blank, or exceeds 30 characters
     *
     * @since 1.0
     */
    public ProjectName {
        if (projectName == null || projectName.isBlank()) {
            throw new IllegalArgumentException("Project projectName must not be null or blank");
        }
        if (projectName.length() > 30) {
            throw new IllegalArgumentException("Project projectName must not exceed 30 characters");
        }
    }
}
