package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SignUpCommand;

import java.util.Optional;

/**
 * UserAccountCommandService
 *
 * @summary
 * Interface defining command operations related to the {@link UserAccount} aggregate.
 * It encapsulates the logic required to register a new system account associated with a person.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface UserAccountCommandService {

    /**
     * Handles the creation of a new {@link UserAccount} and its associated {@link Person}
     * based on the provided {@link SignUpCommand}.
     *
     * @param command the {@link SignUpCommand} containing account credentials and personal data
     * @return an {@link Optional} containing the created {@link UserAccount} if successful
     *
     * @since 1.0
     */
    Optional<UserAccount> handle(SignUpCommand command);
}
