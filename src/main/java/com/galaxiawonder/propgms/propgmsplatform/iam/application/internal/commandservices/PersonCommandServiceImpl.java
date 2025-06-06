package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.CreatePersonCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.EmailAddress;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.PhoneNumber;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonCommandService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * PersonCommandServiceImpl
 *
 * @summary
 * Command service implementation for handling operations related to the {@link Person} aggregate root.
 * It is responsible for executing the logic to create new person records, enforcing uniqueness of email and phone.
 *
 * @author Tralalero
 * @since 1.0
 */
@Service
public class PersonCommandServiceImpl implements PersonCommandService {

    private final PersonRepository personRepository;

    /**
     * Constructs a new PersonCommandServiceImpl with the given repository.
     *
     * @param personRepository the repository used to persist {@link Person} entities
     */
    PersonCommandServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Handles the creation of a new person based on the given {@link CreatePersonCommand}.
     * Validates that email and phone number (if present) are unique before saving the person.
     *
     * @param command the command containing the person creation details
     * @return an {@link Optional} containing the created {@link Person}, if found after save
     * @throws IllegalArgumentException if email or phone number already exists
     */
    public Optional<Person> handle(CreatePersonCommand command) {
        EmailAddress email = new EmailAddress(command.email());
        validateUniqueEmail(email);

        PhoneNumber phone = buildPhoneIfPresent(command.phone());

        var person = new Person(command);

        if (phone != null) {
            validateUniquePhone(phone);
            person.assignPhoneNumber(phone);
        }

        personRepository.save(person);

        return personRepository.findByEmail(email);
    }

    /**
     * Validates that the given email does not already exist in the repository.
     *
     * @param email the email to validate
     * @throws IllegalArgumentException if the email already exists
     */
    private void validateUniqueEmail(EmailAddress email) {
        if (personRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Person with the same email already exists");
        }
    }

    /**
     * Validates that the given phone number does not already exist in the repository.
     *
     * @param phone the phone number to validate
     * @throws IllegalArgumentException if the phone number already exists
     */
    private void validateUniquePhone(PhoneNumber phone) {
        if (personRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("Person with the same phone number already exists");
        }
    }

    /**
     * Builds a {@link PhoneNumber} from the given raw string if it is not null or blank.
     *
     * @param rawPhone the raw phone number string
     * @return a {@link PhoneNumber} instance or null if input is blank
     */
    private PhoneNumber buildPhoneIfPresent(String rawPhone) {
        if (rawPhone == null || rawPhone.isBlank()) return null;
        return new PhoneNumber(rawPhone);
    }
}
