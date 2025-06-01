package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;

/**
 *
 * @param legalName The legal name of the organization.
 * @param commercialName The commercial name of the organization.
 * @param ruc The RUC of the organization.
 * @param createdBy The creator of the organization entity.
 *
 * @throws IllegalArgumentException if any of the validation constraints on the parameters are violated.
 */
public record CreateOrganizationResource(String legalName, String commercialName, Ruc ruc, String createdBy) {
    public CreateOrganizationResource {
        if (legalName == null || legalName.isBlank()) throw new IllegalArgumentException("legalName cannot be null or empty");
        if (ruc.toString() == null || ruc.toString().isBlank()) throw new IllegalArgumentException("RUC cannot be null or empty");
        if (createdBy == null || createdBy.isBlank()) throw new IllegalArgumentException("createdBy cannot be null or empty");
    }
}
