package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects;

/**
 * TaskStatuses
 *
 * @summary
 * Enumeration that defines the possible statuses of a task
 * within the context of project milestone management.
 *
 * These statuses represent the lifecycle of a task from creation
 * through submission and approval stages.
 *
 * @since 1.0
 */
public enum TaskStatuses {

    /** The task has been created but not yet prepared for review. */
    DRAFT,

    /** The task is ready and awaiting submission. */
    PENDING,

    /** The task has a currently non-reviewed submission. */
    SUBMITTED,

    /** The task's last submission has been reviewed and approved. */
    APPROVED,

    /** The task's last submission has been reviewed and rejected. */
    REJECTED
}
