package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
/**
 * Command representing the data required to update the date range of a milestone.
 *
 * <p>This command encapsulates the necessary fields to update the scheduling of a milestone,
 * including its unique identifier and the new date range that defines its start and end dates.</p>
 *
 * <ul>
 *   <li><strong>id</strong>: The unique identifier of the milestone to be updated. Must not be null or less than 1.</li>
 *   <li><strong>DateRange</strong>: The new date range for the milestone, including both start and end dates. Must not be null.</li>
 * </ul>
 *
 * <p>Validation is expected to ensure all required fields meet the defined constraints and business rules.</p>
 *
 * @param id the unique identifier of the milestone
 * @param DateRange the new date range, containing the start and end dates for the milestone
 *
 * @since 1.0
 */
public record UpdateMilestoneDateRangeCommand(Long id, DateRange DateRange) {
}
