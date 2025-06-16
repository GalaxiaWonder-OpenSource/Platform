package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskSubmission;
import jakarta.persistence.Embeddable;

/**
 * TaskSubmissionId
 *
 * @summary
 * Value object that encapsulates the identifier of a {@link TaskSubmission}.
 * Enhances domain expressiveness and type safety when referencing task submissions across the system.
 * Internally wraps a {@code Long} description, which is used as the primary key at the persistence layer.
 *
 * @param taskSubmissionId the numeric identifier of the task submission, must be positive and non-null
 *
 * @since 1.0
 */
@Embeddable
public record TaskSubmissionId(Long taskSubmissionId) {

    /**
     * Validates the {@code taskSubmissionId} description.
     *
     * @throws IllegalArgumentException if {@code taskSubmissionId} is null or less than 1
     */
    public TaskSubmissionId {
        if (taskSubmissionId == null || taskSubmissionId < 1) {
            throw new IllegalArgumentException("TaskSubmission id cannot be null or less than 1");
        }
    }
}
