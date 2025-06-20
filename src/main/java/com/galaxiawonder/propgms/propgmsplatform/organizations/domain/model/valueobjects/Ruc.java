package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Ruc
 *
 * @summary
 * Value object representing a RUC (Registro Único de Contribuyentes), a unique tax identification number
 * commonly used in certain countries like Peru.
 *
 * <p>The RUC must satisfy the following rules:</p>
 * <ul>
 *   <li>Must be exactly 11 digits long</li>
 *   <li>Must startDate with "10" (individual) or "20" (company)</li>
 * </ul>
 *
 * <p>This class is immutable and validated upon instantiation.</p>
 *
 * @param value the RUC string to be validated and stored
 */
@Embeddable
public record Ruc(String value) {

    /**
     * Creates an empty RUC with a blank description.
     * Intended for use by frameworks that require a no-arg constructor.
     */
    public Ruc() {
        this("");
    }

    /**
     * Validates and constructs a {@link Ruc} instance.
     *
     * @param value the RUC description
     *
     * @throws NullPointerException if the provided description is {@code null}
     * @throws IllegalArgumentException if the description is not exactly 11 digits long
     *                                  or does not startDate with "10" or "20"
     */
    public Ruc {
        Objects.requireNonNull(value, "RUC cannot be null");

        if (!value.matches("\\d{11}")) {
            throw new IllegalArgumentException("RUC must have exactly 11 digits");
        }

        if (!(value.startsWith("10") || value.startsWith("20"))) {
            throw new IllegalArgumentException("RUC must begin with '10' or '20'");
        }
    }
}
