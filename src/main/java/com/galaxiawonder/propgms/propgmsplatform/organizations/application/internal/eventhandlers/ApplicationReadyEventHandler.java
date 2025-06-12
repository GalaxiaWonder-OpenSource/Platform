package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.eventhandlers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationMemberTypeCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationStatusCommand;
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
 * Event handler responsible for seeding default organization statuses
 * once the Spring application context is fully initialized.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service("OrganizationApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {

    private final OrganizationStatusCommandService organizationStatusCommandService;
    private final OrganizationMemberTypeCommandService organizationMemberTypeCommandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    public ApplicationReadyEventHandler(OrganizationStatusCommandService organizationStatusCommandService, OrganizationMemberTypeCommandService organizationMemberTypeCommandService) {
        this.organizationStatusCommandService = organizationStatusCommandService;
        this.organizationMemberTypeCommandService = organizationMemberTypeCommandService;
    }

    /**
     * Handles the {@link ApplicationReadyEvent} by triggering the seeding of default organization statuses.
     *
     * @param event the Spring application ready event
     */
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("🔄 Verifying if organization statuses need seeding for {} at {}", applicationName, currentTimestamp());

        organizationStatusCommandService.handle(new SeedOrganizationStatusCommand());

        organizationMemberTypeCommandService.handle(new SeedOrganizationMemberTypeCommand());

        LOGGER.info("✅ Organization statuses seeding completed for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
