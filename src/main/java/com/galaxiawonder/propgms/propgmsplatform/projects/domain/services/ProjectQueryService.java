package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByContractingEntityIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByTeamMemberPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetProjectByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetProjectInfoByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectInfo;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryService {
    List<Project> handle(GetAllProjectsByTeamMemberPersonIdQuery query);

    Optional<ProjectInfo> handle(GetProjectInfoByProjectIdQuery query);

    Optional<Project> handle(GetProjectByProjectIdQuery query);

    Optional<List<Project>> handle(GetAllProjectsByContractingEntityIdQuery query);
}
