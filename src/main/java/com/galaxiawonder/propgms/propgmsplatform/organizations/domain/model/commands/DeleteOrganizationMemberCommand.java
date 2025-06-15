package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;

/**
 * Command used to request the deletion of an {@link OrganizationMember} from the system.
 *
 * <p>
 * This command is typically handled by a service responsible for removing a member
 * from an organization, based on their unique identifier.
 * </p>
 *
 * @param organizationMemberId the unique ID of the member to be removed, as a {@link String}
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record DeleteOrganizationMemberCommand(
        Long organizationMemberId
) {
}

