package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationMemberType;

/**
 * @summary
 * A command object representing the data required to create a new organization member.
 *
 * @param personId the person id linked to the member
 * @param organizationId the organization id linked to the member
 * @param memberType the member's type in the organization
 */
public record CreateOrganizationMemberCommand(Long personId, Long organizationId, OrganizationMemberType memberType) {
    public CreateOrganizationMemberCommand {
        /**
         * @throws IllegalArgumentException if personId is null or zero
         * @throws IllegalArgumentException if organizationId is null or zero
         * @throws IllegalArgumentException if memberType is null
         */
        if (personId == null || personId.equals(0L)) throw new IllegalArgumentException("Person id cannot be null or zero");
        if (organizationId == null || organizationId.equals(0L)) throw new IllegalArgumentException("Organization id cannot be null or zero");
        if (memberType == null) throw new IllegalArgumentException("Member type cannot be null");
    }
}
