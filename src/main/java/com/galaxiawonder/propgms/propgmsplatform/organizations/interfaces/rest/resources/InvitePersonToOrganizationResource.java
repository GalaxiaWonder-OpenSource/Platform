package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources;

/**
 * Resource representing the request to invite a person to an organization.
 * <p>
 * This DTO is typically used in RESTful APIs to carry the data needed
 * to process an invitation command.
 *
 * @param organizationId the ID of the organization to which the person is being invited
 * @param email the email address of the person to be invited
 */
public record InvitePersonToOrganizationResource(
        Long organizationId,
        String email
) {
}
