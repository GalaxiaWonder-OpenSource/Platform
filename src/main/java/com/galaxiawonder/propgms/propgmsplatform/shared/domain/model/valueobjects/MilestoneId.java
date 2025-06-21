package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import jakarta.persistence.Embeddable;

/**
 * MilestoneId
 *
 * @summary
 * Value object that encapsulates the identifier of a {@link Milestone}.
 * Enhances domain expressiveness and type safety when referencing milestones throughout the system.
 * Internally wraps a {@code Long} description, which is used as the primary key at the persistence layer.
 *
 * @param milestoneId the numeric identifier of the milestone, must be positive and non-null
 *
 * @since 1.0
 */
@Embeddable
public record MilestoneId(Long milestoneId) {

    /**
     * Validates the {@code milestoneId} description.
     *
     * @throws IllegalArgumentException if {@code milestoneId} is null or less than 1
     */
    public MilestoneId {
        if (milestoneId == null || milestoneId < 1) {
            throw new IllegalArgumentException("Milestone id cannot be null or less than 1");
        }
    }
}

