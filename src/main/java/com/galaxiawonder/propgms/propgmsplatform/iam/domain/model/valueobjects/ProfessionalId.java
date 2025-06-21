package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * ProfessionalId
 *
 * @summary
 * Value object that encapsulates a validated professional ID for registered engineers or architects in Peru.
 * Valid formats are {@code CIP######} or {@code CAP######}, for Colegio de Ingenieros and Colegio de Arquitectos respectively.
 *
 * Internally wraps a {@code String} description stored as CHAR(9) in the database.
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
     * Initializes the description to an empty string. Not recommended for direct usage.
     */
    protected ProfessionalId() {
        this.value = "";
    }

    /**
     * Constructs a new {@link ProfessionalId} with format validation.
     *
     * @param value the professional ID string, must match either {@code CIP######} or {@code CAP######}
     * @throws NullPointerException     if {@code description} is null
     * @throws IllegalArgumentException if {@code description} does not match the expected format
     */
    public ProfessionalId(String value) {
        Objects.requireNonNull(value, "Professional ID cannot be null");

        if (!isValidCip(value) && !isValidCap(value)) {
            throw new IllegalArgumentException("Professional ID must match 'CIP######' or 'CAP######' format");
        }

        this.value = value;
    }

    /**
     * Checks if a string matches the CIP pattern.
     *
     * @param value the description to check
     * @return true if it matches CIP format; false otherwise
     */
    boolean isValidCip(String value) {
        return CIP_PATTERN.matcher(value).matches();
    }

    /**
     * Checks if a string matches the CAP pattern.
     *
     * @param value the description to check
     * @return true if it matches CAP format; false otherwise
     */
    boolean isValidCap(String value) {
        return CAP_PATTERN.matcher(value).matches();
    }

    /**
     * Returns the professional ID as a string.
     *
     * @return the professional ID
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks for description-based equality.
     *
     * @param o the object to compare
     * @return true if values match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfessionalId that)) return false;
        return value.equals(that.value);
    }

    /**
     * Computes the hash code.
     *
     * @return hash code based on description
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}