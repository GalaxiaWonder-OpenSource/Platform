package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

/**
 * Command to update an organization
 * @param organizationId the organization id.
 * Cannot be null or less than 1.
 * @param commercialName the commercial name of an organization
 * Cannot be null or blank.
 */
public record UpdateOrganizationCommand(Long organizationId, String commercialName) {
    /**
     * Constructor
     * @param organizationId the organization id
     *                       Cannot be null or less than 1.
     * @param commercialName the commercial name of an organization
     *                       Cannot be null or blank
     * @throws IllegalArgumentException if personId is null or less than 1.
     * @throws IllegalArgumentException if commercial name is null or blank
     */
    public UpdateOrganizationCommand {
        if (organizationId == null || organizationId <= 0) {
            throw new IllegalArgumentException("Organization id cannot be null or less than 1");
        }
        if (commercialName == null || commercialName.isBlank()) {
            throw new IllegalArgumentException("Commercial name cannot be null or blank");
        }
    }
}
