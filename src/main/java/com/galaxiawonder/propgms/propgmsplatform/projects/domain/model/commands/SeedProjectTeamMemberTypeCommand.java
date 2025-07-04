package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

/**
 * SeedProjectTeamMemberTypeCommand
 *
 * @summary
 * Command used to trigger the seeding of default project member types into the system.
 * Typically used during application initialization or data bootstrap processes.
 *
 * <p>Examples of member types that may be seeded include:</p>
 * <ul>
 *   <li>{@code COORDINATOR} </li>
 *   <li>{@code SPECISLIST} </li>
 * </ul>
 *
 * <p>This command does not contain parameters, as the seeding logic is predefined within the application layer.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record SeedProjectTeamMemberTypeCommand() {
}
