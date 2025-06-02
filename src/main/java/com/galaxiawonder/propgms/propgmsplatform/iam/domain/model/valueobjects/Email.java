package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Email Value Object
 *
 * @summary
 * Represents a validated email address within the system.
 * This value object ensures proper format and immutability.
 *
 * @since 1.0
 */
@Embeddable
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"
    );

    @Getter
    @Column(name = "email", unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String value;

    /**
     * Default constructor required by JPA.
     */
    protected Email() {
        this.value = "";
    }

    /**
     * Constructs a new {@link Email} value object with format validation.
     *
     * @param value the email address to wrap
     * @throws NullPointerException if the value is null
     * @throws IllegalArgumentException if the value does not match a valid email format
     * @since 1.0
     */
    public Email(String value) {
        Objects.requireNonNull(value, "Email cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        this.value = value.toLowerCase();
    }

    /**
     * Returns the email address as a string.
     *
     * @return the normalized email address
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this email with another object for equality.
     *
     * @param o the object to compare
     * @return true if the other object is a {@link Email} with the same value
     * @since 1.0
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email other)) return false;
        return value.equals(other.value);
    }

    /**
     * Computes the hash code of this email.
     *
     * @return the hash code based on the email value
     * @since 1.0
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
