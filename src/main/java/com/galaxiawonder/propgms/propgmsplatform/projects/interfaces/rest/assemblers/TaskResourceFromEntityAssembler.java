package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Task;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.TaskResource;

public class TaskResourceFromEntityAssembler {
    public static TaskResource toResourceFromEntity(Task entity) {
        return new TaskResource(
                entity.getId(),
                entity.getName().milestoneItemName(),
                entity.getDescription().description(),
                entity.getRange().startDate(),
                entity.getRange().endDate(),
                entity.getMilestoneId().milestoneId(),
                entity.getSpecialty().getStringName(),
                entity.getStatus().getStringName(),
                entity.getPersonId() != null ? entity.getPersonId().personId() : null
        );
    }
}
