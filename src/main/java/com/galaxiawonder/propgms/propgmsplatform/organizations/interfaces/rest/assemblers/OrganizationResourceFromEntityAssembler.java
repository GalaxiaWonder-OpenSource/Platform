package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.OrganizationResource;

/**
 * Assembler to create a OrganizationResource from an Organization entity.
 */
public class OrganizationResourceFromEntityAssembler {
    /**
     * Converts an Organization entity to a Organization.
     * @param entity Organization entity to convert
     * @return OrganizationResource created from the entity
     */
    public static OrganizationResource toResourceFromEntity(Organization entity) {
        return new OrganizationResource(entity.getId(), entity.getLegalName().toString(), entity.getCommercialName().toString(), entity.getRuc().toString(), entity.getCreatedBy().toString(), entity.getStatus().toString());
    }
}
