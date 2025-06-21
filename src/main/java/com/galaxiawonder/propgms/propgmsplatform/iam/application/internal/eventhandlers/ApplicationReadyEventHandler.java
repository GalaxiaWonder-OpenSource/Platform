package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.eventhandlers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SeedUserTypeCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.UserTypeCommandService;
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
 * Event handler responsible for seeding default user types
 * when the Spring application context is fully initialized.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service("IdentityAndAccessManagementApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {

    /** Service for executing commands related to {@link UserType}. */
    private final UserTypeCommandService userTypeCommandService;

    /** Logger instance for recording application events. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    /**
     * Constructs a new {@code ApplicationReadyEventHandler} with the specified command service.
     *
     * @param userTypeCommandService the service used for executing user type commands
     */
    public ApplicationReadyEventHandler(UserTypeCommandService userTypeCommandService) {
        this.userTypeCommandService = userTypeCommandService;
    }

    /**
     * Handles the {@link ApplicationReadyEvent} by triggering the seeding of user types.
     *
     * @param event the Spring application ready event
     */
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Verifying if user types need seeding for {} at {}", applicationName, currentTimestamp());

        var seedCommand = new SeedUserTypeCommand();
        userTypeCommandService.handle(seedCommand);

        LOGGER.info("User types seeding completed for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
