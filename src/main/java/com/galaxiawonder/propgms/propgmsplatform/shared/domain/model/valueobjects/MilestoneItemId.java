package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MilestoneItem;
import jakarta.persistence.Embeddable;

/**
 * MilestoneItemId
 *
 * @summary
 * Value object that encapsulates the identifier of a {@link MilestoneItem}.
 * Enhances type safety and clarity when referencing milestone items across the system.
 * Internally wraps a {@code Long} description, which is used as the primary key at the persistence layer.
 *
 * @param milestoneItemId the numeric identifier of the milestone item, must be positive and non-null
 *
 * @since 1.0
 */
@Embeddable
public record MilestoneItemId(Long milestoneItemId) {

    /**
     * Validates the {@code milestoneItemId} description.
     *
     * @throws IllegalArgumentException if {@code milestoneItemId} is null or less than 1
     */
    public MilestoneItemId {
        if (milestoneItemId == null || milestoneItemId < 1) {
            throw new IllegalArgumentException("Milestone item ID cannot be null or less than 1");
        }
    }
}
