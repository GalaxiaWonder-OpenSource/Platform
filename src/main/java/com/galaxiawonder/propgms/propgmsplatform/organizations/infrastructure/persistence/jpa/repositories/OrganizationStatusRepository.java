package com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * OrganizationStatusRepository
 *
 * @summary
 * JPA repository for managing {@link OrganizationStatus} entities.
 * Provides methods to query organization statuses by name and check for existence.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface OrganizationStatusRepository extends JpaRepository<OrganizationStatus, Long> {

    /**
     * Finds an organization status by its name.
     *
     * @param name the {@link OrganizationStatuses} enum representing the status name
     * @return an {@link Optional} containing the {@link OrganizationStatus} if found
     */
    Optional<OrganizationStatus> findByName(OrganizationStatuses name);

    /**
     * Checks if an organization status exists by its name.
     *
     * @param name the {@link OrganizationStatuses} enum representing the status name
     * @return true if the organization status exists, false otherwise
     */
    boolean existsByName(OrganizationStatuses name);
}
