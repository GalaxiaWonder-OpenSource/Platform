package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Task;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTasksByMilestoneIdAndPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTasksByMilestoneIdQuery;

import java.util.List;
import java.util.Optional;

public interface TaskQueryService {
    Optional<List<Task>> handle(GetAllTasksByMilestoneIdQuery query);
    Optional<List<Task>> handle(GetAllTasksByMilestoneIdAndPersonIdQuery query);
}
