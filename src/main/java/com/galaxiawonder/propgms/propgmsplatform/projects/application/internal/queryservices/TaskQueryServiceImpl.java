package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Task;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTasksByMilestoneIdAndPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTasksByMilestoneIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.TaskQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.TaskRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.MilestoneId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskQueryServiceImpl implements TaskQueryService {
    private final TaskRepository taskRepository;

    public TaskQueryServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<List<Task>> handle(GetAllTasksByMilestoneIdQuery query){
        var milestoneId = new MilestoneId(query.milestoneId());
        return taskRepository.findByMilestoneId(milestoneId);
    }

    public Optional<List<Task>> handle(GetAllTasksByMilestoneIdAndPersonIdQuery query){
        var milestoneId = new MilestoneId(query.milestoneId());
        var personId = new PersonId(query.personId());
        return taskRepository.findByMilestoneIdAndPersonId(milestoneId, personId);
    }
}
