package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands;

/**
 * SeedOrganizationInvitationStatusCommand
 *
 * @summary
 * Command used to trigger the seeding of default organization invitation statuses
 * into the system during initialization or data setup processes.
 *
 * <p>Statuses that may be seeded include:</p>
 * <ul>
 *   <li>{@code PENDING} — the invitation is awaiting a response</li>
 *   <li>{@code ACCEPTED} — the recipient has accepted the invitation</li>
 *   <li>{@code REJECTED} — the recipient has declined the invitation</li>
 * </ul>
 *
 * <p>This command does not carry any additional parameters, as the seeding logic is internally defined.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record SeedOrganizationInvitationStatusCommand() {
}

