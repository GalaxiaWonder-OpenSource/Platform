package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

/**
 * InvitePersonToOrganizationByEmailCommand
 *
 * @summary
 * Command used to invite a person to an organization using their email address.
 * This command triggers the creation of an invitation entry associated with a specific organization.
 *
 * <p>If the person does not exist in the system, the invitation remains pending
 * until registration or acceptance. If they do exist, the invitation is immediately linked to their profile.</p>
 *
 * @param organizationId the ID of the target organization
 * @param email the email address of the person to be invited
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record InvitePersonToOrganizationByEmailCommand(
        Long organizationId,
        String email
) {}