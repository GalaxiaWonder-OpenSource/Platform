package com.galaxiawonder.propgms.propgmsplatform.change.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.SeedChangeProcessStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeProcessStatus;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.services.ChangeProcessStatusCommandService;
import com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories.ChangeProcessStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ChangeProcessStatusCommandServiceImpl implements ChangeProcessStatusCommandService {
    private final ChangeProcessStatusRepository changeProcessStatusRepository;

    public ChangeProcessStatusCommandServiceImpl(ChangeProcessStatusRepository changeProcessStatusRepository) {
        this.changeProcessStatusRepository = changeProcessStatusRepository;
    }

    @Override
    public void handle (SeedChangeProcessStatusCommand command) {
        Arrays.stream(ChangeProcessStatuses.values()).forEach(status -> {
            if (!changeProcessStatusRepository.existsByName(status)) {
                changeProcessStatusRepository.save(new ChangeProcessStatus(status));
            }
        });
    }
}
