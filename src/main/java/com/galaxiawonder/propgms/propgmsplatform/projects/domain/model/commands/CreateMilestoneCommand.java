package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
/**
 * CreateMilestoneCommand
 *
 * @summary
 * Command object representing the data required to create a new milestone within a project.
 *
 * <p>This command encapsulates all necessary fields to define a milestone,
 * including its name, description, scheduled date range, and the associated project identifier.</p>
 *
 * <ul>
 *   <li><strong>Name</strong>: The official name of the milestone. Must not be null or blank.</li>
 *   <li><strong>Description</strong>: A concise description of what the milestone represents. Must not be null or blank.</li>
 *   <li><strong>ProjectId</strong>: The identifier of the project this milestone belongs to. Must not be null.</li>
 *   <li><strong>DateRange</strong>: The planned time span for the milestone, including start and end dates. Must not be null.</li>
 * </ul>
 *
 * <p>Validation is applied to ensure all required fields meet the defined constraints and business rules.</p>
 *
 * @param Name the name of the milestone
 * @param Description a short descriptive summary of the milestone
 * @param ProjectId the ID of the parent project
 * @param DateRange the scheduled start and end dates for the milestone
 *
 * @since 1.0
 */

public record CreateMilestoneCommand(MilestoneName Name, Description Description, ProjectId ProjectId, DateRange DateRange) {
}
