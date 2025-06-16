package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedTaskStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.TaskStatuses;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.TaskStatusCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.TaskStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * TaskStatusCommandService Implementation
 *
 * @summary
 * Service implementation responsible for handling task status command operations,
 * such as seeding default task statuses into the database if they do not exist.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class TaskStatusCommandServiceImpl implements TaskStatusCommandService {

    /** Repository for managing {@link TaskStatus} entities. */
    private final TaskStatusRepository taskStatusRepository;

    /**
     * Constructs a new {@code TaskStatusCommandServiceImpl} with the specified repository.
     *
     * @param taskStatusRepository the repository used to persist {@link TaskStatus} entities
     */
    public TaskStatusCommandServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(SeedTaskStatusCommand command) {
        Arrays.stream(TaskStatuses.values()).forEach(taskStatus -> {
            if (!taskStatusRepository.existsByName(taskStatus)) {
                taskStatusRepository.save(new TaskStatus(taskStatus));
            }
        });
    }
}
