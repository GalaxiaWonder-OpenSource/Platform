package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import java.util.Date;

/**
 * CreateProjectCommand
 *
 * @summary
 * Command object representing the data required to create a new project.
 *
 * <p>This command encapsulates all necessary fields to define a project within the system,
 * including its name, description, planned schedule, associated organization,
 * and contracting entity details.</p>
 *
 * <ul>
 *   <li><strong>projectName</strong>: The official name of the project. Must not be null or blank.</li>
 *   <li><strong>description</strong>: A concise description or summary of the project. Must not be null or blank.</li>
 *   <li><strong>startDate</strong>: The planned start date of the project. Must not be null.</li>
 *   <li><strong>endDate</strong>: The expected end date of the project. Must not be null.</li>
 *   <li><strong>organizationId</strong>: The identifier of the organization responsible for executing the project. Must not be null.</li>
 *   <li><strong>contractingEntityEmail</strong>: Email of the client or entity that requested the project. Must not be null or blank.</li>
 * </ul>
 *
 * <p>Validation is applied to ensure all required fields meet the defined constraints and business rules.</p>
 *
 * @param projectName the name of the project
 * @param description a short descriptive summary
 * @param startDate the planned starting date
 * @param endDate the planned completion date
 * @param organizationId the ID of the responsible organization
 * @param contractingEntityEmail email of the contracting client or entity
 *
 * @since 1.0
 */
public record CreateProjectCommand(
        String projectName,
        String description,
        Date startDate,
        Date endDate,
        Long organizationId,
        String contractingEntityEmail
) {
    public CreateProjectCommand {
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

