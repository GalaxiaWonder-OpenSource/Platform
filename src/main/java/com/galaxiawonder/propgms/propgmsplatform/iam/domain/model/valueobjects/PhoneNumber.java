package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * PhoneNumber Value Object
 *
 * @summary
 * Represents a phone number using international format (E.164).
 * Ensures that the value is valid before persistence.
 *
 * @since 1.0
 */
@Embeddable
public class PhoneNumber {
    private static final Pattern E164_PATTERN = Pattern.compile("^\\+\\d{7,15}$");

    @Getter
    @Column(name = "phone_number", unique = true, length = 16, columnDefinition = "CHAR(16)")
    private String value;

    /**
     * Default constructor for JPA.
     *
     * @summary
     * Required by JPA and other serialization frameworks. Initializes the value to an empty string.
     * Should not be used directly in business logic.
     *
     * @since 1.0
     */
    protected PhoneNumber() {
        this.value = "";
    }

    /**
     * Creates a new {@link PhoneNumber} instance with validation.
     *
     * @param value the phone number in E.164 format (e.g., +51987654321)
     * @throws NullPointerException if the value is null
     * @throws IllegalArgumentException if the value does not match E.164 format
     * @since 1.0
     */
    public PhoneNumber(String value) {
        Objects.requireNonNull(value, "Phone number cannot be null");
        if (!E164_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Phone number must be in international E.164 format (e.g., +51987654321)");
        }
        this.value = value;
    }

    /**
     * Returns the phone number value as a string.
     *
     * @return the phone number in E.164 format (e.g., +51987654321)
     * @since 1.0
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this phone number with another object for equality.
     *
     * @param o the object to compare
     * @return true if the other object is a {@link PhoneNumber} with the same value
     * @since 1.0
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber other)) return false;
        return value.equals(other.value);
    }

    /**
     * Computes the hash code of this phone number.
     *
     * @return the hash code based on the phone number value
     * @since 1.0
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
