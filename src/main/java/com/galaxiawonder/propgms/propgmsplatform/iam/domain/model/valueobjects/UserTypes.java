package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

/**
 * Enum that represents the types of users in the system.
 * <p>
 * This can be used to distinguish between different roles
 * or categories of users such as workers and clients.
 * </p>
 */
public enum UserTypes {
    /**
     * Represents a user who provides work on project consulting.
     */
    TYPE_WORKER,

    /**
     * Represents a client or customer who requests the consulting services.
     */
    TYPE_CLIENT
}