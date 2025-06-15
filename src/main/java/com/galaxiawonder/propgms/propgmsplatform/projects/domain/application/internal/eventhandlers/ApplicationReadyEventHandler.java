package com.galaxiawonder.propgms.propgmsplatform.projects.domain.application.internal.eventhandlers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedProjectStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectStatusCommandService;
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
 * Event handler responsible for seeding default project statuses
 * when the Spring application context is fully initialized.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service("ProjectManagementApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {

    /** Service for executing commands related to {@link ProjectStatus}. */
    private final ProjectStatusCommandService projectStatusCommandService;

    /** Logger instance for recording application events. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    /**
     * Constructs a new {@code ProjectApplicationReadyEventHandler} with the specified command service.
     *
     * @param projectStatusCommandService the service used for executing project status commands
     */
    public ApplicationReadyEventHandler(ProjectStatusCommandService projectStatusCommandService) {
        this.projectStatusCommandService = projectStatusCommandService;
    }

    /**
     * Handles the {@link ApplicationReadyEvent} by triggering the seeding of project statuses.
     *
     * @param event the Spring application ready event
     */
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("🚧 Checking if project statuses need seeding for {} at {}", applicationName, currentTimestamp());

        var seedCommand = new SeedProjectStatusCommand();
        projectStatusCommandService.handle(seedCommand);

        LOGGER.info("✅ Project statuses seeding completed for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
