package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.UpdateProjectResource;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * UpdateProjectCommandFromResourceAssembler
 *
 * @summary
 * Utility class that transforms a {@link UpdateProjectResource} and projectId
 * into a {@link UpdateProjectCommand}. Validates and sanitizes input.
 */
public class UpdateProjectCommandFromResourceAssembler {
    public static final Date NO_UPDATE_DATE = new GregorianCalendar(9999, Calendar.DECEMBER, 31).getTime();

    /**
     * Converts a {@link UpdateProjectResource} and a projectId into an {@link UpdateProjectCommand}.
     * Performs validation and null-safe sanitization.
     *
     * @param projectId the ID of the project to update
     * @param resource the incoming resource object from the API layer
     * @return a validated and sanitized {@code UpdateProjectCommand}
     * @throws IllegalArgumentException if required fields are invalid
     */
    public static UpdateProjectCommand toCommandFromResource(Long projectId, UpdateProjectResource resource) {
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("projectId cannot be null or less than 1");
        }

        String name = (resource.name() == null) ? "" : resource.name();
        String description = (resource.description() == null) ? "" : resource.description();
        String status = (resource.status() == null) ? "" : resource.status();
        Date endingDate = (resource.endingDate() == null) ? NO_UPDATE_DATE : resource.endingDate();

        return new UpdateProjectCommand(
                projectId,
                name,
                description,
                status,
                endingDate
        );
    }
}
