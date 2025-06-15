package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources;

public record OrganizationResource(
        Long id,
        String legalName,
        String commercialName,
        String ruc,
        Long createdBy,
        String status
) {
}
