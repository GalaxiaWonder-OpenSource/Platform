package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateProjectResource;

/**
 * CreateProjectCommandFromResourceAssembler
 *
 * @summary
 * Utility class that transforms a {@link CreateProjectResource} into a {@link CreateProjectCommand}.
 * Used to map incoming API resource data into a domain-level command for project creation.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class CreateProjectCommandFromResourceAssembler {

    /**
     * Converts a {@link CreateProjectResource} into a {@link CreateProjectCommand}.
     *
     * @param resource the incoming resource object from the API layer
     * @return a {@code CreateProjectCommand} populated with data from the resource
     */
    public static CreateProjectCommand toCommandFromResource(CreateProjectResource resource) {
        return new CreateProjectCommand(
                resource.projectName(),
                resource.description(),
                resource.startDate(),
                resource.endDate(),
                resource.organizationId(),
                resource.contractingEntityEmail()
        );
    }
}
