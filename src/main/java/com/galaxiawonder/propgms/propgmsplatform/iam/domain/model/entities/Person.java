package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.Email;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.PhoneNumber;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.ProfessionalId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    /** First name of the person (max 50 characters, not blank) */
    @NotBlank
    @Getter
    @Size(max = 50)
    private String firstname;

    /** Last name of the person (max 50 characters, not blank) */
    @NotBlank
    @Getter
    @Size(max = 50)
    private String lastname;

    /** Unique email of the person, represented as a value object */
    @Embedded
    @Getter
    private Email email;

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
    public Person(String firstname, String lastname, Email email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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

