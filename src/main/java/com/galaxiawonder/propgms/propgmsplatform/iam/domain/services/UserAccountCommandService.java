package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SignInCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

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

    /**
     * Handles the authentication of a user account based on provided credentials.
     *
     * @summary
     * Validates the username and password contained in the {@link SignInCommand}.
     * If authentication is successful, returns an {@code ImmutablePair} containing the {@link UserAccount}
     * and a newly generated authentication token (e.g., JWT).
     *
     * @param command the {@link SignInCommand} containing the username and raw password
     * @return an {@code Optional} containing an {@code ImmutablePair} of {@link UserAccount} and token string
     *         if authentication succeeds; {@code Optional.empty()} otherwise
     *
     * @throws IllegalArgumentException if the username is malformed or credentials are invalid
     * @since 1.0
     */
    Optional<ImmutablePair<UserAccount, String>> handle(SignInCommand command);
}
