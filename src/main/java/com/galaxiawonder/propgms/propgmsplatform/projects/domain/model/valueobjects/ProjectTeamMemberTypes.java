package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects;
/**
 * Represents the member's type in an organization.
 * It provides two possible types:
 * - COORDINATOR: Project member with all the permissions.
 * - SPECIALIST: Project member with some permissions.
 */
public enum ProjectTeamMemberTypes {
    COORDINATOR,
    SPECIALIST
}
