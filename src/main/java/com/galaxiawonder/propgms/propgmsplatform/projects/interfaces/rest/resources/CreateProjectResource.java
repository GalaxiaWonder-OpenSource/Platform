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
 * @throws IllegalArgumentException if any of the validation constraints on the parameters are violated.
 */
public record CreateProjectResource(
        String projectName,
        String description,
        Date startDate,
        Date endDate,
        Long organizationId,
        String contractingEntityEmail
) {
    public CreateProjectResource {
        if (projectName == null || projectName.isBlank()) {
            throw new IllegalArgumentException("projectName cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("startDate cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("endDate cannot be null");
        }
        if (organizationId == null) {
            throw new IllegalArgumentException("organizationId cannot be null");
        }
        if (contractingEntityEmail == null || contractingEntityEmail.isBlank()) {
            throw new IllegalArgumentException("contractingEntityEmail cannot be null or blank");
        }
    }
}