package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import java.util.Date;

/**
 * Resource representing the data required to create a new project via REST.
 *
 * @param projectName the name of the project
 * @param description the description of the project
 * @param startDate the expected start date
 * @param endDate the expected end date
 * @param organizationId the ID of the organization responsible
 * @param contractingEntityEmail the email of the person/entity contracting the project
 */
public record CreateProjectResource(
        String projectName,
        String description,
        Date startDate,
        Date endDate,
        Long organizationId,
        String contractingEntityEmail
) {}
