package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTeamMembersByProjectIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProjectTeamMemberQueryService {
    Optional<List<ProjectTeamMember>> handle(GetAllTeamMembersByProjectIdQuery query);
}
