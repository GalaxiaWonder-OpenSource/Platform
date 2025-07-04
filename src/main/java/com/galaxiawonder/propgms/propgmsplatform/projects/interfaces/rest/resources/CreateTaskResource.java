package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import java.util.Date;
/**
 * Resource representing the data required to create a new task within a milestone.
 *
 * <p>This resource carries all necessary fields to define a task,
 * including its name, description, associated milestone ID, specialty,
 * planned schedule with start and end dates, status, and optionally the assigned person.</p>
 *
 * <ul>
 *   <li><strong>name</strong>: Name of the task. Must not be null or blank.</li>
 *   <li><strong>description</strong>: Description of the task with purpose and objectives. Must not be null or blank.</li>
 *   <li><strong>startDate</strong>: The planned start date of the task. Must not be null.</li>
 *   <li><strong>endDate</strong>: The planned end date of the task. Must not be null.</li>
 *   <li><strong>milestoneId</strong>: The unique identifier of the milestone to which this task belongs. Must not be null.</li>
 *   <li><strong>specialty</strong>: The specialty or discipline associated with the task. Must not be null or blank.</li>
 *   <li><strong>status</strong>: The initial status of the task. May be null (optional).</li>
 *   <li><strong>personId</strong>: The identifier of the person assigned to the task. May be null (optional).</li>
 * </ul>
 *
 * @param name the name of the task
 * @param description the description of the task with purpose and objectives
 * @param startDate the planned start date of the task
 * @param endDate the planned end date of the task
 * @param milestoneId the unique identifier of the milestone this task belongs to
 * @param specialty the specialty or discipline associated with the task
 * @param status the initial status of the task (optional)
 * @param personId the identifier of the assigned person (optional)
 *
 * @since 1.0
 */
public record CreateTaskResource(String name, String description, Date startDate, Date endDate, Long milestoneId, String specialty, String status, Long personId) {
}
