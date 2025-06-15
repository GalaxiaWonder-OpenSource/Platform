package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ProjectStatusRepository
 *
 * @summary
 * JPA repository for managing {@link ProjectStatus} entities.
 * Provides methods to query project statuses by name and check for existence.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {

    /**
     * Finds a project status by its name.
     *
     * @param name the {@link ProjectStatuses} enum representing the status name
     * @return an {@link Optional} containing the {@link ProjectStatus} if found
     */
    Optional<ProjectStatus> findByName(ProjectStatuses name);

    /**
     * Checks if a project status exists by its name.
     *
     * @param name the {@link ProjectStatuses} enum representing the status name
     * @return true if the project status exists, false otherwise
     */
    boolean existsByName(ProjectStatuses name);
}
