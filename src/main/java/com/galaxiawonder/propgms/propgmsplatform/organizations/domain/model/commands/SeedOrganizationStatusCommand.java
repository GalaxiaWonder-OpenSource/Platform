package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

/**
 * SeedOrganizationStatusCommand
 *
 * @summary
 * Command used to initialize the system with default organization statuses.
 * Typically executed during the application startup or data seeding process.
 *
 * <p>Examples of statuses that may be seeded: {@code ACTIVE}, {@code INACTIVE}, {@code SUSPENDED}.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record SeedOrganizationStatusCommand() {
}
