package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * PhoneNumber
 *
 * @summary
 * Value object that encapsulates a phone number in E.164 format.
 * Ensures correctness at creation and enables strong typing within the domain.
 *
 * @param value the phone number in international format (e.g., +51987654321)
 *
 * @since 1.0
 */
@Embeddable
public record PhoneNumber(
        @Column(name = "phone_number", unique = true, length = 16, columnDefinition = "VARCHAR(16)")
        String value
) {
    private static final Pattern E164_PATTERN = Pattern.compile("^\\+\\d{7,15}$");

    /**
     * Default constructor for JPA.
     * Initializes the phone number as {@code null}.
     */
    public PhoneNumber() {
        this(null);
    }

    /**
     * Constructor with validation.
     *
     * @param value the phone number to validate and store
     * @throws NullPointerException     if the phone number is null
     * @throws IllegalArgumentException if the phone number does not match the E.164 format
     */
    public PhoneNumber {
        Objects.requireNonNull(value, "Phone number cannot be null");
        if (!E164_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Phone number must be in international E.164 format (e.g., +51987654321)");
        }
    }

    /**
     * Returns the string representation of the phone number.
     *
     * @return the phone number in E.164 format
     */
    @Override
    public String toString() {
        return value;
    }
}