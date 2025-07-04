package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.Specialty;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneItemName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.MilestoneId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
/**
 * Command representing the data required to create a new task within a milestone.
 *
 * <p>This command encapsulates all necessary fields to define a task,
 * including its name, description, associated milestone, specialty,
 * planned schedule, assigned person, and optional status.</p>
 *
 * <ul>
 *   <li><strong>name</strong>: The official name of the task. Must not be null.</li>
 *   <li><strong>description</strong>: A concise description of the task. Must not be null.</li>
 *   <li><strong>dateRange</strong>: The planned start and end dates for the task. Must not be null.</li>
 *   <li><strong>milestoneId</strong>: The identifier of the milestone this task belongs to. Must not be null.</li>
 *   <li><strong>specialty</strong>: The specialty or discipline associated with the task. Must not be null.</li>
 *   <li><strong>status</strong>: The current status of the task. May be null (optional).</li>
 *   <li><strong>personId</strong>: The identifier of the person assigned to the task. May be null (optional).</li>
 * </ul>
 *
 * <p>Validation is applied to ensure all required fields meet the defined constraints and business rules.</p>
 *
 * @param name the name of the task
 * @param description a short descriptive summary of the task
 * @param dateRange the planned start and end dates for the task
 * @param milestoneId the ID of the parent milestone
 * @param specialty the specialty or discipline associated with the task
 * @param status the current status of the task (optional)
 * @param personId the ID of the assigned person (optional)
 *
 * @since 1.0
 */
public record CreateTaskCommand(MilestoneItemName name, Description description, DateRange dateRange, MilestoneId milestoneId, Specialty specialty, TaskStatus status, PersonId personId) {
    /**
     * Compact constructor to enforce validation rules.
     *
     * @throws IllegalArgumentException if any required field is null or invalid
     */
    public CreateTaskCommand {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
        if (dateRange == null || dateRange.startDate() == null || dateRange.endDate() == null) {
            throw new IllegalArgumentException("dateRange and its start and end dates cannot be null");
        }
        if (milestoneId == null) {
            throw new IllegalArgumentException("milestoneId cannot be null");
        }
        if (specialty == null) {
            throw new IllegalArgumentException("specialty cannot be null");
        }
    }
}
