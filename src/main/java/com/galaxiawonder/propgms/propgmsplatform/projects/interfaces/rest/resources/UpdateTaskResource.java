package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneItemName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.annotation.Nullable;

import java.util.Date;
/**
 * Resource representing the data required to update a task.
 *
 * <p>This resource carries all fields that can be modified in a task,
 * including its name, description, schedule, status, assigned person,
 * and a flag to remove the person assignment.</p>
 *
 * <ul>
 *   <li><strong>id</strong>: The unique identifier of the task to be updated. Must not be null.</li>
 *   <li><strong>name</strong>: The updated name of the task. May be null (optional).</li>
 *   <li><strong>description</strong>: The updated description of the task. May be null (optional).</li>
 *   <li><strong>startDate</strong>: The updated planned start date. May be null (optional).</li>
 *   <li><strong>endDate</strong>: The updated planned end date. May be null (optional).</li>
 *   <li><strong>status</strong>: The updated status of the task. May be null (optional).</li>
 *   <li><strong>personId</strong>: The ID of the assigned person. May be null (optional).</li>
 *   <li><strong>removePerson</strong>: Flag indicating whether to remove the assigned person. May be null (optional).</li>
 * </ul>
 *
 * @param name the updated name of the task (optional)
 * @param description the updated description of the task (optional)
 * @param status the updated status of the task (optional)
 * @param personId the ID of the assigned person (optional)
 * @param removePerson flag indicating whether to remove the assigned person (optional)
 *
 * @since 1.0
 */
public record UpdateTaskResource (@Nullable String name,
                                  @Nullable String description,
                                  @Nullable Date startDate,
                                  @Nullable Date endDate,
                                  @Nullable String status,
                                  @Nullable Long personId,
                                  @Nullable Boolean removePerson){
}
