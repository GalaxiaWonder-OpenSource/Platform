package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.CreatePersonCommand;

import java.util.Optional;

/**
 * PersonCommandService
 *
 * @summary
 * Interface defining command operations related to the Person entity.
 * It includes functionality for creating a new person in the system.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface PersonCommandService {

    /**
     * Handles the creation of a new {@link Person} using the provided command.
     *
     * @param command the {@link CreatePersonCommand} containing the personal information to be registered
     * @since 1.0
     */
    Optional<Person> handle(CreatePersonCommand command);
}
