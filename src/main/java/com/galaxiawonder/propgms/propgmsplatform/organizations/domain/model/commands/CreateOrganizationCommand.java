package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

import jakarta.annotation.Nullable;

/**
 * CreateOrganizationCommand
 *
 * @summary
 * Command object representing the data required to create a new organization.
 *
 * <p>This command encapsulates the necessary fields for creating an organization source, including:</p>
 * <ul>
 *   <li><strong>legalName</strong>: The legal projectName of the organization. Must not be null or blank.</li>
 *   <li><strong>commercialName</strong>: The commercial projectName of the organization. Can be null.</li>
 *   <li><strong>ruc</strong>: The RUC (tax ID) of the organization. Must follow valid format.</li>
 *   <li><strong>createdBy</strong>: The identifier of the user/entity that created the organization. Must not be null.</li>
 * </ul>
 *
 * <p>Validation is applied to ensure all required fields meet defined constraints.</p>
 *
 * @param legalName the legal projectName of the organization
 * @param commercialName the commercial projectName of the organization (optional)
 * @param ruc the tax identification number (RUC) of the organization
 * @param createdBy the ID of the creator
 */
public record CreateOrganizationCommand(String legalName, @Nullable String commercialName, String ruc, Long createdBy) {

    /**
     * Constructs a new {@link CreateOrganizationCommand} with the given data and validates required fields.
     *
     * @throws IllegalArgumentException if legalName is null or blank
     * @throws IllegalArgumentException if ruc is null or blank
     * @throws IllegalArgumentException if createdBy is null
     */
    public CreateOrganizationCommand {
        if (legalName == null || legalName.isBlank()) {
            throw new IllegalArgumentException("legalName cannot be null or blank");
        }
        if (ruc == null || ruc.isBlank()) {
            throw new IllegalArgumentException("ruc cannot be null or blank");
        }
        if (createdBy == null) {
            throw new IllegalArgumentException("createdBy cannot be null");
        }
    }
}