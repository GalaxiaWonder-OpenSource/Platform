package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SignUpCommand;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.EmailAddress;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonName;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.PhoneNumber;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.ProfessionalId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

/**
 * Person
 *
 * @summary
 * Domain entity representing a real-world person within the system.
 * It encapsulates identity and personal data such as name, email, optional phone number, and optional professional ID.
 * Lifecycle audit timestamps are automatically managed through JPA auditing.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Table(name = "persons")
public class Person extends AuditableAbstractAggregateRoot<Person> {
    /** Full name of the person, encapsulated in a value object */
    @Getter
    @Embedded
    private PersonName name;

    /** Unique email of the person, represented as a value object */
    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email"))})
    private EmailAddress email;

    /** Optional phone number of the person, represented as a value object */
    @Embedded
    @Getter
    private PhoneNumber phone;

    /** Optional professional ID (e.g., CIP/CAP), represented as a value object */
    @Embedded
    @Getter
    private ProfessionalId professionalId;

    /**
     * Protected constructor required by JPA.
     * Should not be used directly in application code.
     */
    protected Person() {}

    /**
     * Constructs a new Person instance with required fields.
     *
     * @param firstname the first name of the person
     * @param lastname the last name of the person
     * @param email the email of the person, wrapped in a value object
     */
    public Person(String firstname, String lastname, String email) {
        this.name = new PersonName(firstname, lastname);
        this.email = new EmailAddress(email);
    }

    /**
     * Constructs a Person instance from a {@link SignUpCommand}.
     * Maps the input values into appropriate value objects.
     *
     * @param command the command object containing signup data
     */
    public Person(SignUpCommand command) {
        this.name = new PersonName(
                command.firstName(),
                command.lastName()
        );
        this.email = new EmailAddress(command.email());

        if (command.phone() != null) {
            this.phone = new PhoneNumber(command.phone());
        }
    }

    /**
     * Assigns a phone number to this person.
     *
     * @param phone the phone number value object
     * @throws NullPointerException if phone is null
     */
    public void assignPhoneNumber(PhoneNumber phone) {
        this.phone = Objects.requireNonNull(phone, "Phone number is required");
    }

    /**
     * Assigns a professional ID (e.g., registration number) to this person.
     *
     * @param professionalId the professional ID value object
     * @throws NullPointerException if professionalId is null
     */
    public void assignProfessionalId(ProfessionalId professionalId) {
        this.professionalId = Objects.requireNonNull(professionalId, "Professional ID is required");
    }

    public PersonId getIdAsValueObject() {
        return new PersonId(this.getId());
    }

    public boolean hasThisPersonId(PersonId personId) {
        return Objects.equals(personId.personId(), this.getId());
    }
}