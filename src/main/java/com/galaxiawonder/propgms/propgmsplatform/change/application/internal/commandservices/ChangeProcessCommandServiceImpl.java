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
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectInfo;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.acl.ProjectContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangeProcessCommandServiceImpl implements ChangeProcessCommandService {

    private final ChangeProcessRepository changeProcessRepository;
    private final ChangeOriginRepository changeOriginRepository;
    private final ChangeProcessStatusRepository changeProcessStatusRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectRepository projectRepository;
    private final ProjectContextFacade projectContextFacade;

    public ChangeProcessCommandServiceImpl(
            ChangeProcessRepository changeProcessRepository,
            ChangeOriginRepository changeOriginRepository,
            ChangeProcessStatusRepository changeProcessStatusRepository,
            ProjectStatusRepository projectStatusRepository,
            ProjectRepository projectRepository,
            ProjectContextFacade projectContextFacade) {
        this.changeProcessRepository = changeProcessRepository;
        this.changeOriginRepository = changeOriginRepository;
        this.changeProcessStatusRepository = changeProcessStatusRepository;
        this.projectStatusRepository = projectStatusRepository;
        this.projectRepository = projectRepository;
        this.projectContextFacade = projectContextFacade;
    }

    @Transactional
    public Optional<ChangeProcess> handle(CreateChangeProcessCommand command) {
        var changeProcess = new ChangeProcess(command);
        var project = projectRepository.findById(command.projectId().projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with id " + command.projectId().projectId() + " does not exist"));

        var existingChangeProcess = changeProcessRepository.findFirstByProjectIdOrderByCreatedAtDesc(command.projectId());
        if (existingChangeProcess.isPresent() && existingChangeProcess.get().getStatus().getId() != 3){
            throw new IllegalArgumentException("Project with given Id is not in a valid state to create a change process");
        }
        var originName = project.getStatus().getName().name().equals("APPROVED")
                ? "TECHNICAL_QUERY"
                : "CHANGE_REQUEST";

        var origin = changeOriginRepository.findByName(ChangeOrigins.valueOf(originName))
                .orElseThrow(() -> new IllegalArgumentException("Change Origin with name " + originName + " does not exist"));

        var status = changeProcessStatusRepository.findByName(ChangeProcessStatuses.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Change Process Status with name " + ChangeProcessStatuses.PENDING + " does not exist"));

        changeProcess.SetInformation(origin, status);
        changeProcessRepository.save(changeProcess);

        var projectStatus = projectStatusRepository.findByName(ProjectStatuses.CHANGE_REQUESTED)
                .orElseThrow(() -> new IllegalArgumentException("Project Status with name " + ProjectStatuses.CHANGE_REQUESTED + " does not exist"));

        project.reassignStatus(projectStatus);
        projectRepository.save(project);

        return Optional.of(changeProcess);
    }

    @Transactional
    public Optional<ChangeProcess> handle(RespondToChangeCommand command) {
        if (command.id() <= 0){
            throw new IllegalArgumentException("Invalid Change Process Id");
        }
        var changeProcess = changeProcessRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Change Process with id " + command.id() + " does not exist"));

        var project = projectRepository.findById(changeProcess.getProjectId().projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with id " + changeProcess.getProjectId().projectId() + " does not exist"));

        var newStatus = command.status().name();
        var status = changeProcessStatusRepository.findByName(ChangeProcessStatuses.valueOf(newStatus))
                .orElseThrow(() -> new IllegalArgumentException("Change Process Status with name " + newStatus + " does not exist"));
        changeProcess.respondToChange(status, command.response());

        var newProjectStatus = command.status().name().equals("APPROVED")
                ? "CHANGE_PENDING"
                : project.getPreviousStatusName();
        if(!newProjectStatus.isBlank()) {
            var projectStatus = projectStatusRepository.findByName(ProjectStatuses.valueOf(newProjectStatus))
                    .orElseThrow(() -> new IllegalArgumentException("Project Status with name " + newProjectStatus + " does not exist"));
            project.reassignStatus(projectStatus);
        }
        projectRepository.save(project);
        changeProcessRepository.save(changeProcess);

        return Optional.of(changeProcess);
    }
}