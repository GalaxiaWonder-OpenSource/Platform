package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources;

/**
 * Resource representing the data required to create a new organization via REST.
 * @param legalName The legal projectName of the organization.
 * @param commercialName The commercial projectName of the organization.
 * @param ruc The RUC of the organization.
 * @param createdBy The creator of the organization entity.
 *
 * @throws IllegalArgumentException if any of the validation constraints on the parameters are violated.
 */
public record CreateOrganizationResource(String legalName, String commercialName, String ruc, Long createdBy) {
    public CreateOrganizationResource {
        if (legalName == null || legalName.isBlank()) throw new IllegalArgumentException("legalName cannot be null or empty");
        if (ruc == null || ruc.isBlank()) throw new IllegalArgumentException("RUC cannot be null or empty");
        if (createdBy == null) throw new IllegalArgumentException("createdBy cannot be null or empty");
    }
}
