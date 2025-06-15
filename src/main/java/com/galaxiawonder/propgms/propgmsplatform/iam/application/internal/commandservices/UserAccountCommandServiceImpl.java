package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.outboundservices.hashing.HashingService;
import com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.outboundservices.tokens.TokenService;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SignInCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SignUpCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.*;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.UserAccountCommandService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.PersonRepository;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.UserAccountRepository;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.UserTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

/**
 * UserAccountCommandServiceImpl
 *
 * @summary
 * Implements the {@link UserAccountCommandService} interface to handle the creation of user accounts
 * and associated persons. Validates uniqueness of credentials and persists the required entities using
 * injected JPA repositories.
 *
 * This service is transactional and ensures atomic creation of both {@link UserAccount} and {@link Person}.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class UserAccountCommandServiceImpl implements UserAccountCommandService {
    /**
     * JPA repository for user accounts.
     */
    private final UserAccountRepository userAccountRepository;

    /**
     * JPA repository for persons.
     */
    private final PersonRepository personRepository;

    /**
     * JPA repository for user types.
     */
    private final UserTypeRepository userTypeRepository;

    /**
     * Password hashing service for securely storing credentials.
     */
    private final HashingService hashingService;

    /**
     * Token generation service for issuing authentication tokens.
     */
    private final TokenService tokenService;

    /**
     * Constructs the service with all necessary dependencies.
     *
     * @param userAccountRepository repository for {@link UserAccount} persistence
     * @param personRepository repository for {@link Person} persistence
     * @param userTypeRepository repository for {@link UserType} lookup
     * @param hashingService utility for password hashing and verification
     */
    UserAccountCommandServiceImpl(
            UserAccountRepository userAccountRepository,
            PersonRepository personRepository,
            UserTypeRepository userTypeRepository,
            HashingService hashingService,
            TokenService tokenService) {
        this.userAccountRepository = userAccountRepository;
        this.personRepository = personRepository;
        this.userTypeRepository = userTypeRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Optional<UserAccount> handle(SignUpCommand command) {
        validateUniqueAccountData(command);

        Person person = new Person(command);
        personRepository.save(person);

        UserType userType = getUserTypeFromDatabase(command)
                .orElseThrow(() -> new IllegalArgumentException("Invalid userType"));

        String hashedPassword = encodePassword(command);
        UserAccount userAccount = new UserAccount(
                command.username(),
                hashedPassword,
                userType
        );
        userAccount.assignPersonId(person.getId());
        userAccountRepository.save(userAccount);

        return getUserAccountFromDatabase(command.username());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ImmutablePair<UserAccount, String>> handle(SignInCommand command) {
        var userAccount = getUserAccountFromDatabase(command.username());

        if (userAccount.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserAccount existingUser = userAccount.get();
        if(!signInPasswordMatchesHash(command, existingUser)) {
            throw new RuntimeException("Invalid password");
        }

        var token = tokenService.generateToken(existingUser);
        return Optional.of(ImmutablePair.of(existingUser, token));
    }


    /**
     * Retrieves the user type from the database using the enum name provided in the command.
     *
     * @param command the sign-up command containing the user type name
     * @return an {@code Optional<UserType>} if found
     * @throws IllegalArgumentException if the user type does not exist
     */
    private Optional<UserType> getUserTypeFromDatabase(SignUpCommand command) {
        UserTypes enumValue = UserTypes.valueOf(command.userType());
        return userTypeRepository.findByName(enumValue);
    }

    /**
     * Hashes the raw password from the command using the configured {@link HashingService}.
     *
     * @param command the sign-up command containing the raw password
     * @return the hashed password string
     */
    private String encodePassword(SignUpCommand command) {
        return hashingService.encode(command.password());
    }

    /**
     * Validates that the username, email, and phone number (if present) are unique.
     *
     * @param command the sign-up command containing the data to be validated
     * @throws IllegalArgumentException if any of the values already exist in the system
     */
    private void validateUniqueAccountData(SignUpCommand command) {
        if (isUserNameTaken(new UserName(command.username()))) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (isEmailTaken(new EmailAddress(command.email()))) {
            throw new IllegalArgumentException("Email address is already taken.");
        }
        if (isPhoneNumberTaken(new PhoneNumber(command.phone()))) {
            throw new IllegalArgumentException("Person with the same phone number already exists");
        }
    }

    /**
     * Checks if a username is already taken.
     *
     * @param name the {@link UserName} to check
     * @return {@code true} if the username exists
     */
    private boolean isUserNameTaken(UserName name) {
        return userAccountRepository.existsByUserName(name);
    }

    /**
     * Checks if an email address is already registered.
     *
     * @param email the {@link EmailAddress} to check
     * @return {@code true} if the email exists
     */
    private boolean isEmailTaken(EmailAddress email) {
        return personRepository.existsByEmail(email);
    }

    /**
     * Checks if a phone number is already registered.
     *
     * @param phone the {@link PhoneNumber} to check
     * @return {@code true} if the phone number exists
     */
    private boolean isPhoneNumberTaken(PhoneNumber phone) {
        return personRepository.existsByPhone(phone);
    }

    /**
     * Verifies if the provided raw password matches the stored hashed password for the user.
     *
     * @param command       the {@link SignInCommand} containing the plain password
     * @param existingUser  the {@link UserAccount} retrieved from the database
     * @return true if the password matches; false otherwise
     */
    private boolean signInPasswordMatchesHash(SignInCommand command, UserAccount existingUser) {
        return hashingService.matches(command.password(), existingUser.getHashedPassword().hashedPassword());
    }

    /**
     * Retrieves a {@link UserAccount} from the database using the provided username.
     *
     * @param command the raw username string
     * @return an {@link Optional} containing the user account if found; empty otherwise
     */
    private Optional<UserAccount> getUserAccountFromDatabase(String command) {
        return userAccountRepository.findByUserName(new UserName(command));
    }

}
