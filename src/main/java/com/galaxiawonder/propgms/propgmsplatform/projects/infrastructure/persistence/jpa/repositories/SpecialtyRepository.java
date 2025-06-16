package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.Specialty;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Specialties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * SpecialtyRepository
 *
 * @summary
 * JPA repository for managing {@link Specialty} entities.
 * Provides methods to query project specialities by name and check for existence.
 *
 * Typically used during system initialization or when assigning domain-specific roles.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    /**
     * Finds a project speciality by its name.
     *
     * @param name the {@link Specialties} enum representing the speciality name
     * @return an {@link Optional} containing the {@link Specialty} if found
     */
    Optional<Specialty> findByName(Specialties name);

    /**
     * Checks if a project speciality exists by its name.
     *
     * @param name the {@link Specialties} enum representing the speciality name
     * @return true if the speciality exists, false otherwise
     */
    boolean existsByName(Specialties name);
}
