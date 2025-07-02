package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.CreateOrganizationResource;

/**
 * CreateOrganizationCommandFromResourceAssembler
 *
 * @summary
 * Utility class that transforms a {@link CreateOrganizationResource} into a {@link CreateOrganizationCommand}.
 * Used to map incoming API resource data into a domain-level command for organization creation.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class CreateOrganizationCommandFromResourceAssembler {
    /**
     * Converts a {@link CreateOrganizationResource} into a {@link CreateOrganizationCommand}.
     * @param resource the incoming resource object from the API layer
     * @return a {@code CreateOrganizationCommand} populated with data from the resource
     */
    public static CreateOrganizationCommand toCommandFromResource(CreateOrganizationResource resource) {
        return new CreateOrganizationCommand(resource.legalName(), resource.commercialName(), resource.ruc(), resource.createdBy());
    }
}
