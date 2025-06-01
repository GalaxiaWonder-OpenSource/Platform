package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.CreateOrganizationResource;

/**
 * Assembler to create a CreateOrganizationCommand from a CreateOrganizationResource
 */
public class CreateOrganizationCommandFromResourceAssembler {
    /**
     * Converts a CreateOrganizationResource to a CreateOrganizationCommand.
     * @param resource CreateOrganizationResource to convert
     * @return CreateOrganizationCommand created from the resource
     */
    public static CreateOrganizationCommand toCommandFromResource(CreateOrganizationResource resource) {
        return new CreateOrganizationCommand(resource.legalName(), resource.commercialName(), resource.ruc(), resource.createdBy());
    }
}
