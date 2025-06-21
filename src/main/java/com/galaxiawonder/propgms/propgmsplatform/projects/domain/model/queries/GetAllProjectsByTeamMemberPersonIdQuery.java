package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;

/**
 * Query object used to retrieve all {@link Project} entities
 * in which a specific person is registered as a member.
 *
 * <p>
 * This query is typically handled by a query service that searches through
 * all {@link ProjectTeamMember} associations to return the list of organizations
 * linked to the given person ID.
 * </p>
 *
 * @param personId the unique identifier of the person whose associated organizations are being requested
 *
 * @since 1.0
 */
public record GetAllProjectsByTeamMemberPersonIdQuery(
        Long personId
) {
}
