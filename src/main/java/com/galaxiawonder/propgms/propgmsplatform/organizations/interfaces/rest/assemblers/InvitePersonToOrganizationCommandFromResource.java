package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.InvitePersonToOrganizationByEmailCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.InvitePersonToOrganizationResource;

/**
 * Assembler class for converting {@link InvitePersonToOrganizationResource} into
 * {@link InvitePersonToOrganizationByEmailCommand}.
 */
public class InvitePersonToOrganizationCommandFromResource {

    /**
     * Converts an {@link InvitePersonToOrganizationResource} to an {@link InvitePersonToOrganizationByEmailCommand}.
     *
     * @param resource the resource containing the organization ID and the email of the person to invite
     * @return the corresponding command object
     */
    public static InvitePersonToOrganizationByEmailCommand toCommandFromResource(
            InvitePersonToOrganizationResource resource) {
        return new InvitePersonToOrganizationByEmailCommand(resource.organizationId(), resource.email());
    }
}

