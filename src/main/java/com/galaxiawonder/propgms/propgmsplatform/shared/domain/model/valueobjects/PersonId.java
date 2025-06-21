package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import jakarta.persistence.Embeddable;

/**
 * PersonId
 *
 * @summary
 * Value object that encapsulates the identifier of a {@link Person}.
 * Improves domain expressiveness and ensures type safety when referencing persons across the system.
 * Internally wraps a {@code Long} description which is still used as the primary key at the persistence layer.
 *
 * @param personId the numeric identifier of the person, must be positive and non-null
 *
 * @since 1.0
 */
@Embeddable
public record PersonId(Long personId) {

    /**
     * Validates the {@code personId} description.
     *
     * @throws IllegalArgumentException if {@code personId} is null or less than 1
     */
    public PersonId {
        if (personId == null || personId < 1) {
            throw new IllegalArgumentException("Profile id cannot be null or less than 1");
        }
    }
}


