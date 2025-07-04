package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneName;
/**
 * Command representing the data required to update the name of a milestone.
 *
 * <p>This command encapsulates the necessary fields to update the official name
 * of a milestone, including its unique identifier and the new name value.</p>
 *
 * <ul>
 *   <li><strong>id</strong>: The unique identifier of the milestone to be updated. Must not be null or less than 1.</li>
 *   <li><strong>MilestoneName</strong>: The new official name for the milestone. Must not be null or blank.</li>
 * </ul>
 *
 * <p>Validation is expected to ensure all required fields meet the defined constraints and business rules.</p>
 *
 * @param id the unique identifier of the milestone
 * @param milestoneName the new official name for the milestone
 *
 * @since 1.0
 */
public record UpdateMilestoneNameCommand(Long id, MilestoneName milestoneName) {
    /**
     * Compact constructor to enforce validation rules.
     *
     * @throws IllegalArgumentException if any field is null or invalid
     */
    public UpdateMilestoneNameCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id cannot be null or less than 1");
        }
        if (milestoneName == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
    }
}
