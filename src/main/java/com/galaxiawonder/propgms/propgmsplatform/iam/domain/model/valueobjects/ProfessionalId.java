package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * ProfessionalId Value Object
 *
 * @summary
 * Represents a validated professional registration code for engineers or architects in Peru.
 * This value object supports codes from the Colegio de Ingenieros del Perú (CIP) and
 * the Colegio de Arquitectos del Perú (CAP).
 *
 * @since 1.0
 */
@Embeddable
public class ProfessionalId {

    private static final Pattern CIP_PATTERN = Pattern.compile("^CIP\\d{6}$");
    private static final Pattern CAP_PATTERN = Pattern.compile("^CAP\\d{6}$");

    @Getter
    @Column(name = "professional_id", columnDefinition = "CHAR(9)", length = 9)
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
    protected ProfessionalId() {
        this.value = "";
    }

    /**
     * Creates a new {@link ProfessionalId} with format validation.
     *
     * @param value the professional ID string, which must match either:
     *              <ul>
     *                  <li><code>CIP######</code> — for the Colegio de Ingenieros del Perú</li>
     *                  <li><code>CAP######</code> — for the Colegio de Arquitectos del Perú</li>
     *              </ul>
     *
     * @throws NullPointerException if the value is null
     * @throws IllegalArgumentException if the value does not match the expected format
     *
     * @since 1.0
     */
    public ProfessionalId(String value) {
        Objects.requireNonNull(value, "Professional ID cannot be null");

        if (!isValidCip(value) && !isValidCap(value)) {
            throw new IllegalArgumentException("Professional ID must match 'CIP######' or 'CAP######' format");
        }

        this.value = value;
    }

    /**
     * Checks whether the given value matches the CIP (Colegio de Ingenieros del Perú) pattern.
     *
     * @param value the string to validate as a CIP code
     * @return true if the value matches the CIP pattern; false otherwise
     */
    boolean isValidCip(String value) {
        return CIP_PATTERN.matcher(value).matches();
    }

    /**
     * Checks whether the given value matches the CAP (Colegio de Arquitectos del Perú) pattern.
     *
     * @param value the string to validate as a CAP code
     * @return true if the value matches the CAP pattern; false otherwise
     */
    boolean isValidCap(String value) {
        return CAP_PATTERN.matcher(value).matches();
    }

    /**
     * Returns the professional ID as a string.
     *
     * @return the validated professional ID
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this professional ID with another object for equality.
     *
     * @param o the object to compare
     * @return true if the other object is a {@link ProfessionalId} with the same value
     * @since 1.0
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfessionalId that)) return false;
        return value.equals(that.value);
    }

    /**
     * Computes the hash code for the professional ID.
     *
     * @return the hash code based on the professional ID value
     * @since 1.0
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
