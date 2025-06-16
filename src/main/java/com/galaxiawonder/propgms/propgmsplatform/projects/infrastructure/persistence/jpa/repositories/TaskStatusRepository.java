package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.TaskStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * TaskStatusRepository
 *
 * @summary
 * JPA repository for managing {@link TaskStatus} entities associated with tasks.
 * Provides methods to query task statuses by name and check for existence.
 *
 * This repository is specific to statuses tied to task lifecycle management.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

    /**
     * Finds a task status by its name.
     *
     * @param name the {@link TaskStatuses} enum representing the task status name
     * @return an {@link Optional} containing the {@link TaskStatus} if found
     */
    Optional<TaskStatus> findByName(TaskStatuses name);

    /**
     * Checks if a task status exists by its name.
     *
     * @param name the {@link TaskStatuses} enum representing the task status name
     * @return true if the task status exists, false otherwise
     */
    boolean existsByName(TaskStatuses name);
}
