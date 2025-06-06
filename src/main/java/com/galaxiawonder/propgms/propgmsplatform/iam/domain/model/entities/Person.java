package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.CreatePersonCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.EmailAddress;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.PersonName;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.PhoneNumber;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.ProfessionalId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
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
public class Person extends AuditableModel {
    @Embedded
    private PersonName name;

    /** Unique email of the person, represented as a value object */
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
     * Constructor from CreatePersonCommand
     * @param command command The {@link CreatePersonCommand} instance
     */
    public Person(CreatePersonCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
    }

    /**
     * First name getter
     * @return First name
     */
    public String getFirstName() {
        return name.firstName();
    }

    /**
     * Second name getter
     * @return First name
     */
    public String getLastName() {
        return name.lastName();
    }

    /**
     * Full name getter
     * @return Full name
     */
    public String getFullName() {
        return name.getFullName();
    }

    /**
     * Email address getter
     * @return Email address
     */
    public String getEmail() {
        return email.address();
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
     * Assigns a professional ID to this person.
     *
     * @param professionalId the professional ID value object
     * @throws NullPointerException if professionalId is null
     */
    public void assignProfessionalId(ProfessionalId professionalId) {
        this.professionalId = Objects.requireNonNull(professionalId, "Professional ID is required");
    }
}

