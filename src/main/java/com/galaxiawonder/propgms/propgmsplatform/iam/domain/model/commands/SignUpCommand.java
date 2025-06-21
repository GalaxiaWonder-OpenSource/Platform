package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import jakarta.annotation.Nullable;

/**
 * SignUpCommand
 *
 * @summary
 * Command object used to request the creation of a new {@link UserAccount} and its associated {@link Person}.
 * Encapsulates all required and optional user data needed for the signup process.
 *
 * This command follows the principles of CQRS (Command Query Responsibility Segregation),
 * separating write intentions from read models.
 *
 * @param username   the unique identifier to be used for authentication
 * @param password   the plain (pre-hash) password provided by the user
 * @param userType   the user category (e.g., TYPE_CLIENT, TYPE_WORKER) to determine permissions and role
 * @param firstName  the person's first projectName
 * @param lastName   the person's last projectName
 * @param email      the person's email address (must be unique and valid)
 * @param phone      the person's optional phone number, must be in E.164 format (e.g., +51987654321) if present
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record SignUpCommand(
        String username,
        String password,
        String userType,
        String firstName,
        String lastName,
        String email,
        @Nullable String phone
) {}
