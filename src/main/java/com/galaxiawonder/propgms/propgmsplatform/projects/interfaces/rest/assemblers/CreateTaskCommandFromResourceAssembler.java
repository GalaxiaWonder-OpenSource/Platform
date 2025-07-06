package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateTaskCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateTaskResource;

public class CreateTaskCommandFromResourceAssembler {
    public static CreateTaskCommand toCommandFromResource(CreateTaskResource resource) {
        Long person = null;
        if (resource.personId() != null) {
            person = resource.personId();
        }
        String status = null;
        if (resource.status() != null && !resource.status().isBlank()) {
            status = resource.status();
        }
        return new CreateTaskCommand(
                resource.name(),
                resource.description(),
                resource.startDate(),
                resource.endDate(),
                resource.milestoneId(),
                resource.specialty(),
                status,
                person

        );
    }
}
