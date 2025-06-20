package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import java.util.Date;

/**
 * CreateProjectCommand
 *
 * @summary
 * Command object used to encapsulate the data required to create a new project.
 * It includes information such as name, description, date range, associated organization,
 * and the email of the contracting entity.
 *
 * @param projectName the name of the project to be created
 * @param description a short description or summary of the project
 * @param startDate the expected starting date of the project
 * @param endDate the expected completion date of the project
 * @param organizationId the ID of the organization responsible for the project
 * @param contractingEntityEmail the email address of the person or entity that requested or contracted the project
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
}
