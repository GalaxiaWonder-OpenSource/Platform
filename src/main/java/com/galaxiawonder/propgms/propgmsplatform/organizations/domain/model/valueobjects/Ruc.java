package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Represents a RUC, a unique tax identification number in certain countries.
 *
 * The RUC must follow these validation rules:
 * - It must be exactly 11 characters long, consisting only of digits.
 * - It must begin with either "10" or "20".
 *
 * @param value the RUC value, which must not be null and must meet the specified validation rules.
 * @throws NullPointerException if the provided value is null.
 * @throws IllegalArgumentException if the value does not meet the validation rules (not exactly 11 digits or does not start with "10" or "20").
 */
@Embeddable
public record Ruc(String value) {

    public Ruc() {
        this("");
    }

    public Ruc {
        Objects.requireNonNull(value, "RUC cannot be null");

        if (!value.matches("\\d{11}")) {
            throw new IllegalArgumentException("RUC must have exactly 11 digits");
        }

        if (!(value.startsWith("10") || value.startsWith("20"))) {
            throw new IllegalArgumentException("RUC must begin with '10' or '20'");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}

