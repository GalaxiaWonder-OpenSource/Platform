package com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationInvitationStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * OrganizationInvitationStatusRepository
 *
 * @summary
 * JPA repository for managing {@link OrganizationInvitationStatus} entities.
 * Provides methods to query invitation statuses by name and check for existence.
 *
 * <p>This repository maps {@link OrganizationInvitationStatuses} enum values
 * to persisted status entities used throughout the organization invitation process.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface OrganizationInvitationStatusRepository extends JpaRepository<OrganizationInvitationStatus, Long> {

    /**
     * Finds an organization invitation status by its enum name.
     *
     * @param name the {@link OrganizationInvitationStatuses} enum representing the status name
     * @return an {@link Optional} containing the {@link OrganizationInvitationStatus} if found
     */
    Optional<OrganizationInvitationStatus> findByName(OrganizationInvitationStatuses name);

    /**
     * Checks if an organization invitation status exists by its enum name.
     *
     * @param name the {@link OrganizationInvitationStatuses} enum representing the status name
     * @return true if the invitation status exists, false otherwise
     */
    boolean existsByName(OrganizationInvitationStatuses name);
}
