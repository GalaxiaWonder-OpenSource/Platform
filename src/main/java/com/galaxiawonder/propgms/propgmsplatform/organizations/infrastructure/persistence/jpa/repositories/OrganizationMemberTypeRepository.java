package com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMemberType;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationMemberTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * OrganizationMemberTypeRepository
 *
 * @summary
 * JPA repository for managing {@link OrganizationMemberType} entities.
 * Provides methods to query organization member types by name and check for existence.
 *
 * <p>This repository maps {@link OrganizationMemberTypes} enum values to persisted entities,
 * allowing the system to maintain a list of predefined member roles (e.g., {@code CONTRACTOR}, {@code WORKER}).</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface OrganizationMemberTypeRepository extends JpaRepository<OrganizationMemberType, Long> {

    /**
     * Finds an organization member type by its name.
     *
     * @param name the {@link OrganizationMemberTypes} enum representing the member type name
     * @return an {@link Optional} containing the {@link OrganizationMemberType} if found
     */
    Optional<OrganizationMemberType> findByName(OrganizationMemberTypes name);

    /**
     * Checks if an organization member type exists by its name.
     *
     * @param name the {@link OrganizationMemberTypes} enum representing the member type name
     * @return true if the organization member type exists, false otherwise
     */
    boolean existsByName(OrganizationMemberTypes name);
}

