package com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeProcessStatus;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Change Process Status Repository
 *
 * @summary
 * JPA Repository for managing {@link ChangeProcessStatus} entities.
 * Provides methods to query Change Process Statuses
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface ChangeProcessStatusRepository extends JpaRepository<ChangeProcessStatus, Long> {

    /**
     * Finds a Change Process Status b its name
     * @param name the {@link ChangeProcessStatus} enum representing the status name.
     * @return an {@link Optional} containing the {@link ChangeProcessStatus} if its found
     */
    Optional<ChangeProcessStatus> findByName(ChangeProcessStatuses name);
}