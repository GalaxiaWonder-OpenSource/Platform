package com.galaxiawonder.propgms.propgmsplatform.change.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.CreateChangeProcessCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.RespondToChangeCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeOrigin;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeProcessStatus;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeOrigins;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeResponse;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.Justification;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.services.ChangeProcessCommandService;
import com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories.ChangeOriginRepository;
import com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories.ChangeProcessRepository;
import com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories.ChangeProcessStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangeProcessCommandServiceImpl implements ChangeProcessCommandService {

    private final ChangeProcessRepository changeProcessRepository;
    private final ChangeOriginRepository changeOriginRepository;
    private final ChangeProcessStatusRepository changeProcessStatusRepository;

    public ChangeProcessCommandServiceImpl(
            ChangeProcessRepository changeProcessRepository,
            ChangeOriginRepository changeOriginRepository,
            ChangeProcessStatusRepository changeProcessStatusRepository) {
        this.changeProcessRepository = changeProcessRepository;
        this.changeOriginRepository = changeOriginRepository;
        this.changeProcessStatusRepository = changeProcessStatusRepository;
    }

    @Transactional
    public Optional<ChangeProcess> handle(CreateChangeProcessCommand command) {
        ChangeOrigin origin = changeOriginRepository.findByName(ChangeOrigins.valueOf(command.origin()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid origin ID"));

        ChangeProcessStatus status = command.status() != null
                ? changeProcessStatusRepository.findByName(ChangeProcessStatuses.valueOf(command.status()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid status enum"))
                : changeProcessStatusRepository.findByName(ChangeProcessStatuses.valueOf("PENDING"))
                .orElseThrow(() -> new IllegalStateException("Default status PENDING not found"));

        var justification = new Justification(command.justification());
        var projectId = new ProjectId(command.projectId());

        var changeProcess = new ChangeProcess(origin, status, justification, projectId);
        changeProcessRepository.save(changeProcess);

        return Optional.of(changeProcess);
    }

    @Transactional
    public Optional<ChangeProcess> handle(Long changeProcessId, RespondToChangeCommand command) {
        ChangeProcess changeProcess = changeProcessRepository.findById(changeProcessId)
                .orElseThrow(() -> new IllegalArgumentException("ChangeProcess not found"));

        ChangeProcessStatus newStatus = changeProcessStatusRepository.findByName(command.status())
                .orElseThrow(() -> new IllegalArgumentException("Invalid status enum"));

        var response = new ChangeResponse(command.notes());
        changeProcess.respondToChange(newStatus, response);

        changeProcessRepository.save(changeProcess);

        return Optional.of(changeProcess);
    }
}