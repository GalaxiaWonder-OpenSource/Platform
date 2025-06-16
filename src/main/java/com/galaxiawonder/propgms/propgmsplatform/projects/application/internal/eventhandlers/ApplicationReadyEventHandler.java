package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.eventhandlers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedProjectStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedSpecialtyCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectStatusCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.SpecialtyCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * ProjectApplicationReadyEventHandler
 *
 * @summary
 * Event handler responsible for seeding default data (statuses and specialties)
 * when the Spring application context is fully initialized.
 *
 * This includes:
 * - Project Statuses (e.g., DESIGN_IN_PROCESS, APPROVED)
 * - Project Specialties (e.g., ARCHITECTURE, SANITATION)
 *
 * This operation is typically executed once on application startup.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service("ProjectManagementApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {

    /** Service for executing commands related to project statuses. */
    private final ProjectStatusCommandService projectStatusCommandService;

    /** Service for executing commands related to project specialties. */
    private final SpecialtyCommandService specialtyCommandService;

    /** Logger instance for recording application events. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    /**
     * Constructs a new {@code ApplicationReadyEventHandler} with the specified command services.
     *
     * @param projectStatusCommandService the service used for seeding project statuses
     * @param specialtyCommandService the service used for seeding project specialties
     */
    public ApplicationReadyEventHandler(ProjectStatusCommandService projectStatusCommandService,
                                        SpecialtyCommandService specialtyCommandService) {
        this.projectStatusCommandService = projectStatusCommandService;
        this.specialtyCommandService = specialtyCommandService;
    }

    /**
     * Handles the {@link ApplicationReadyEvent} by triggering the seeding of statuses and specialties.
     *
     * @param event the Spring application ready event
     */
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Checking if project statuses need seeding for {} at {}", applicationName, currentTimestamp());

        projectStatusCommandService.handle(new SeedProjectStatusCommand());
        specialtyCommandService.handle(new SeedSpecialtyCommand());

        LOGGER.info("Project statuses seeding completed for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
