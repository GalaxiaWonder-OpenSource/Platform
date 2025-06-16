package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserTypeRepository
 *
 * @summary
 * JPA repository for managing {@link UserType} entities.
 * Provides methods to query user types by projectName and check for existence.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

    /**
     * Finds a user type by its projectName.
     *
     * @param name the {@link UserTypes} enum representing the user type projectName
     * @return an {@link Optional} containing the {@link UserType} if found
     */
    Optional<UserType> findByName(UserTypes name);

    /**
     * Checks if a user type exists by its projectName.
     *
     * @param name the {@link UserTypes} enum representing the user type projectName
     * @return true if the user type exists, false otherwise
     */
    boolean existsByName(UserTypes name);
}