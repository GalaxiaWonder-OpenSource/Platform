package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByTeamMemberPersonIdQuery;

import java.util.List;

public interface ProjectQueryService {
    List<Project> handle(GetAllProjectsByTeamMemberPersonIdQuery query);
}
