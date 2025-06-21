package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import java.util.Date;

/**
 * Resource that represents a Project to be returned from the API.
 *
 * @param id the unique identifier of the project
 * @param projectName the name of the project
 * @param description the description of the project
 * @param status the name of the project status
 * @param startDate the start of the date range
 * @param endDate the end of the date range
 * @param organizationId the associated organization ID
 * @param contractingEntityId the person who contracted the project
 */
public record ProjectResource(
        Long id,
        String projectName,
        String description,
        String status,
        Date startDate,
        Date endDate,
        Long organizationId,
        Long contractingEntityId
) {}
