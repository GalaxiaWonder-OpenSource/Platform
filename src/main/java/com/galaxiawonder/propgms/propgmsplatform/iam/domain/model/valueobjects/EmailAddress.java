package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;

/**
 * EmailAddress
 *
 * @summary
 * Value object that encapsulates an email address with format validation.
 * Provides type safety and enforces domain rules across the system.
 *
 * This class is embeddable in aggregates such as {@link Person}.
 *
 * @param address the string representation of the email address, must follow a valid format
 *
 * @see jakarta.validation.constraints.Email
 * @since 1.0
 */
@Embeddable
public record EmailAddress(@Email String address) {

    /**
     * Default constructor required by JPA.
     * Initializes the address as {@code null}.
     */
    public EmailAddress() {
        this(null);
    }
}
