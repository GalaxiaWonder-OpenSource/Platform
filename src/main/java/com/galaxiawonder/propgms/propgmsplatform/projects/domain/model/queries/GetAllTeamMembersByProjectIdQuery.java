package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;

/**
 * Query object used to retrieve all {@link ProjectTeamMember} entities
 * associated with a specific {@link Project}, identified by its unique ID.
 *
 * <p>
 * This query is typically handled by a service that fetches the list of members
 * currently registered under the specified project.
 * </p>
 *
 * @param projectId the unique identifier of the project whose members are being requested
 *
 * @since 1.0
 */
public record GetAllTeamMembersByProjectIdQuery(
        Long projectId
) {
}
