package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllMilestonesByProjectIdQuery;

import java.util.List;
import java.util.Optional;

public interface MilestoneQueryService {
    Optional<List<Milestone>> handle(GetAllMilestonesByProjectIdQuery query);
}
