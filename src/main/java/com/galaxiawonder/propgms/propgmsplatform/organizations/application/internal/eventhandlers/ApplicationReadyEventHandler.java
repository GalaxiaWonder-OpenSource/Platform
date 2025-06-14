package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.eventhandlers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationInvitationStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationMemberTypeCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMemberType;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationInvitationStatusCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationMemberTypeCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationStatusCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * ApplicationReadyEventHandler
 *
 * @summary
 * Event handler responsible for seeding default data related to organizations
 * once the Spring application context is fully initialized.
 *
 * <p>This includes:</p>
 * <ul>
 *   <li>Seeding default organization statuses (e.g., {@code ACTIVE}, {@code INACTIVE})</li>
 *   <li>Seeding default organization member types (e.g., {@code CONTRACTOR}, {@code WORKER})</li>
 *   <li>Seeding default organization invitation statuses (e.g., {@code PENDING}, {@code ACCEPTED}, {@code REJECTED})</li>
 * </ul>
 *
 * <p>The seeding process is idempotent and will only persist missing values.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service("OrganizationApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {
    /** Service for seeding {@link OrganizationStatus} entities. */
    private final OrganizationStatusCommandService organizationStatusCommandService;

    /** Service for seeding {@link OrganizationMemberType} entities. */
    private final OrganizationMemberTypeCommandService organizationMemberTypeCommandService;

    /** Service for seeding {@link OrganizationInvitationStatus} entities. */
    private final OrganizationInvitationStatusCommandService organizationInvitationStatusCommandService;

    /** Logger for application lifecycle events. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    /**
     * Constructs a new {@code ApplicationReadyEventHandler} with the required command services.
     *
     * @param organizationStatusCommandService service to initialize organization statuses
     * @param organizationMemberTypeCommandService service to initialize member types
     * @param organizationInvitationStatusCommandService service to initialize invitation statuses
     */
    public ApplicationReadyEventHandler(OrganizationStatusCommandService organizationStatusCommandService, OrganizationMemberTypeCommandService organizationMemberTypeCommandService, OrganizationInvitationStatusCommandService organizationInvitationStatusCommandService) {
        this.organizationStatusCommandService = organizationStatusCommandService;
        this.organizationMemberTypeCommandService = organizationMemberTypeCommandService;
        this.organizationInvitationStatusCommandService = organizationInvitationStatusCommandService;
    }

    /**
     * Handles the {@link ApplicationReadyEvent} by triggering the seeding of default organization, organization members and organization invitation statuses.
     *
     * @param event the Spring application ready event
     */
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("🔄 Verifying if organization statuses need seeding for {} at {}", applicationName, currentTimestamp());

        organizationStatusCommandService.handle(new SeedOrganizationStatusCommand());

        organizationMemberTypeCommandService.handle(new SeedOrganizationMemberTypeCommand());

        organizationInvitationStatusCommandService.handle(new SeedOrganizationInvitationStatusCommand());

        LOGGER.info("✅ Organization statuses seeding completed for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
