package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

import lombok.Getter;

import java.util.Objects;

/**
 * Value Object representing the ID of an Organization Member.
 *
 * <p>This class ensures that the ID is never null or invalid within the domain.
 * It provides a default constructor required for serialization frameworks like Jackson or JPA.</p>
 *
 * @since 1.0
 */
@Getter
public class OrganizationMemberId {
    private Long value;
    /**
     * Default constructor required by some serialization frameworks.
     * Do not use this in domain logic directly.
     */
    public OrganizationMemberId() {
        // intentionally empty
    }
    /**
     * Constructs a valid OrganizationMemberId.
     *
     * @param value the ID value, must be positive
     */
    public OrganizationMemberId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("OrganizationMemberId must be a positive number");
        }
        this.value = value;
    }
    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationMemberId that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "null";
    }
}
