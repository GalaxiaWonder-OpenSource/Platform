package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
/**
 * Command representing the data required to update the description of a milestone.
 *
 * <p>This command encapsulates the necessary fields to update the textual description
 * of a milestone, including its unique identifier and the new descriptive content.</p>
 *
 * <ul>
 *   <li><strong>id</strong>: The unique identifier of the milestone to be updated. Must not be null or less than 1.</li>
 *   <li><strong>MilestoneDescription</strong>: The new description for the milestone. Must not be null or blank.</li>
 * </ul>
 *
 * <p>Validation is expected to ensure all required fields meet the defined constraints and business rules.</p>
 *
 * @param id the unique identifier of the milestone
 * @param milestoneDescription the new descriptive content for the milestone
 *
 * @since 1.0
 */
public record UpdateMilestoneDescriptionCommand(Long id, Description milestoneDescription) {
    /**
     * Compact constructor to enforce validation rules.
     *
     * @throws IllegalArgumentException if any field is null or invalid
     */
    public UpdateMilestoneDescriptionCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id cannot be null or less than 1");
        }
        if (milestoneDescription == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
    }
}
