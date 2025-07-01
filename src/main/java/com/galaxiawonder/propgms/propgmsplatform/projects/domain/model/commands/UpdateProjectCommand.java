package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;

import java.util.Date;

/**
 * Command representing the data required to update a Project.
 *
 * @param name the name of the project. Must not be null, blank, or shorter than 3 characters.
 * @param description the description of the project. Must not be null or blank.
 * @param status the status of the project. Must not be null.
 * @param endingDate the ending date of the project. Must not be null.
 */
public record UpdateProjectCommand(
        Long projectId,
        String name,
        String description,
        ProjectStatuses status,
        Date endingDate
) {
    /**
     * Creates an instance of UpdateProjectCommand ensuring all fields meet validation rules.
     *
     * @throws IllegalArgumentException if any field is invalid
     */
    public UpdateProjectCommand {
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("Project id cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("name must be at least 3 characters long");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }
        if (endingDate == null) {
            throw new IllegalArgumentException("endingDate cannot be null");
        }
    }
}