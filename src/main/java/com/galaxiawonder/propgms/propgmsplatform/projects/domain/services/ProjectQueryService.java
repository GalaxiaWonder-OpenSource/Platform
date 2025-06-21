package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByTeamMemberPersonIdQuery;

import java.util.List;

public interface ProjectQueryService {
    /**
     * Handles the query to retrieve all {@link Project} entities in which
     * the specified person is currently registered as a project team member.
     *
     * <p>
     * This method performs the following actions:
     * <ul>
     *   <li>Searches for all {@link ProjectTeamMember} records linked to the given {@code personId}.</li>
     *   <li>Retrieves the corresponding {@link Project} instances associated with those team memberships.</li>
     *   <li>Returns the complete list of projects in which the person is actively involved.</li>
     * </ul>
     * </p>
     *
     * @param query the {@link GetAllProjectsByTeamMemberPersonIdQuery} containing the target person's ID
     * @return a {@link List} of {@link Project} entities where the person is a registered team member
     *
     * @since 1.0
     */
    List<Project> handle(GetAllProjectsByTeamMemberPersonIdQuery query);
}
