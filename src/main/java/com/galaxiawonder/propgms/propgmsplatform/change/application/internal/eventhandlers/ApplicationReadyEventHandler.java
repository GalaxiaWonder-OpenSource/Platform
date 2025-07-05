package com.galaxiawonder.propgms.propgmsplatform.change.application.internal.eventhandlers;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.SeedChangeOriginCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.SeedChangeProcessStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.services.ChangeOriginCommandService;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.services.ChangeProcessStatusCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class ApplicationReadyEventHandler {
    private final ChangeOriginCommandService changeOriginCommandService;
    private final ChangeProcessStatusCommandService changeProcessStatusCommandService;

    private static final Logger LOGGER = LoggerFactory.getLogger(com.galaxiawonder.propgms.propgmsplatform.change.application.internal.eventhandlers.ApplicationReadyEventHandler.class);

    public ApplicationReadyEventHandler(ChangeOriginCommandService changeOriginCommandService, ChangeProcessStatusCommandService changeProcessStatusCommandService) {
        this.changeOriginCommandService = changeOriginCommandService;
        this.changeProcessStatusCommandService = changeProcessStatusCommandService;
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Verifying if change origin and change process status need seeding for {} at {}", applicationName, currentTimestamp());

        changeOriginCommandService.handle(new SeedChangeOriginCommand());
        changeProcessStatusCommandService.handle(new SeedChangeProcessStatusCommand());

        LOGGER.info("Change origin and Change process status seeding completed for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
