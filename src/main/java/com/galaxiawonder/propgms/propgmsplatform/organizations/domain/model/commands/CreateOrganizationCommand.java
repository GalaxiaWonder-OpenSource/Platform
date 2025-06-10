package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

import jakarta.annotation.Nullable;

/**
 * @summary
 * A command object representing the data required to create a new organization source.
 *
 * This command encapsulates the necessary fields for creating an organization source, including:
 * - legalName: The legal name of the organization. Must not be null or empty.
 * - CommercialName: The commercial name of the organization, which can be null.
 * - ruc: The RUC of the organization.
 *        Must be a valid RUC as per the defined validation rules.
 * - createdBy: The identifier of the entity or user who created the organization. Must not be null or empty.
 * - status: The operational status of the organization. Must not be null.
 *
 * Validation is applied to ensure all required fields are properly set and meet the necessary constraints:
 * - legalName must not be null or blank.
 * - RUC must meet the predefined formatting and validation rules.
 * - createdBy must not be null or blank.
 * - status must not be null.
 *
 * @param legalName The legal name of the organization.
 * @param commercialName The commercial name of the organization.
 * @param ruc The RUC of the organization.
 * @param createdBy The creator of the organization entity.
 *
 * @throws IllegalArgumentException if any of the validation constraints on the parameters are violated.
 */
public record CreateOrganizationCommand(String legalName, @Nullable String commercialName, String ruc, Long createdBy) {
    public CreateOrganizationCommand {
        if (legalName == null || legalName.isBlank()) throw new IllegalArgumentException("legalName cannot be null or empty");
        if (ruc== null || ruc.isBlank()) throw new IllegalArgumentException("RUC cannot be null or empty");
        if (createdBy == null) throw new IllegalArgumentException("createdBy cannot be null or empty");
    }
}
