package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateTaskCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateTaskResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.UpdateTaskResource;

public class UpdateTaskCommandFromResourceAssembler {
    public static UpdateTaskCommand toCommandFromResource(Long taskId, UpdateTaskResource resource){
        return new UpdateTaskCommand(
                taskId,
                resource.name() != null ? resource.name() : null,
                resource.description() != null ? resource.description() :  null,
                resource.startDate() != null ? resource.startDate() : null,
                resource.endDate() != null ? resource.endDate() : null,
                resource.status() != null ? resource.status() : null,
                resource.personId() != null ? resource.personId() : null,
                resource.removePerson()
        );
    }
}
