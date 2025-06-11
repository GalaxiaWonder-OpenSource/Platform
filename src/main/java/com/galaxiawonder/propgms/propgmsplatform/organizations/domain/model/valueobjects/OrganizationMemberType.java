package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects;
/**
 * Represents the member's type in an organization.
 * It provides two possible types:
 * - CONTRACTOR: Organization's owner, with all the permissions.
 * - WORKER: Organization's worker, with some permissions.
 */
public enum OrganizationMemberType {
    CONTRACTOR,
    WORKER
}
