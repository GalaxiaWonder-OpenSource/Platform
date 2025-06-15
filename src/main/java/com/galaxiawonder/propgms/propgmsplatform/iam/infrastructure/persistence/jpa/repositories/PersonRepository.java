package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.EmailAddress;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * PersonRepository
 *
 * @summary
 * Repository interface for managing persistence operations related to the Person aggregate.
 * Extends JpaRepository to provide basic CRUD operations and includes custom query methods
 * for checking existence and retrieving persons by attributes.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    /**
     * Checks if a person with the given email already exists.
     *
     * @param email the email to check for existence
     * @return true if a person with the specified email exists, false otherwise
     */
    boolean existsByEmail(EmailAddress email);

    /**
     * Checks if a person with the given phone number already exists.
     *
     * @param phone the phone number to check for existence
     * @return true if a person with the specified phone number exists, false otherwise
     */
    boolean existsByPhone(PhoneNumber phone);

    /**
     * Finds a person entity by their email address.
     *
     * @param emailAddress the {@link EmailAddress} value object representing the person's email
     * @return an {@link Optional} containing the {@link Person} if found, or {@link Optional#empty()} if no match exists
     *
     * @since 1.0
     */
    Optional<Person> findByEmail(EmailAddress emailAddress);
}
