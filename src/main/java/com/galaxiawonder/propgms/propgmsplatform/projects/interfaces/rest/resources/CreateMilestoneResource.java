package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import java.util.Date;
/**
 * Resource representing the data required to create a new milestone.
 *
 * <p>This resource carries all necessary fields to define a milestone,
 * including its name, description, associated project ID, and its planned schedule
 * with start and end dates.</p>
 *
 * <ul>
 *   <li><strong>Name</strong>: Name of the milestone. Must not be null or blank.</li>
 *   <li><strong>Description</strong>: Description of the milestone with purpose and objectives. Must not be null or blank.</li>
 *   <li><strong>ProjectId</strong>: The unique identifier of the project to which this milestone belongs. Must not be null.</li>
 *   <li><strong>StartDate</strong>: The start date of the milestone. Must not be null.</li>
 *   <li><strong>EndDate</strong>: The end date of the milestone. Must not be null.</li>
 * </ul>
 *
 * @param name the name of the milestone
 * @param description the description of the milestone with purpose and objectives
 * @param projectId the unique identifier of the project this milestone belongs to
 * @param startDate the start date of the milestone
 * @param endDate the end date of the milestone
 *
 * @since 1.0
 */

public record CreateMilestoneResource(String name, String description, Long projectId, Date startDate, Date endDate) {
}
