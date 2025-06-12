package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import jakarta.persistence.Embeddable;

/**
 * OrganizationId
 *
 * @summary
 * Value object that encapsulates the identifier of an {@link Organization}.
 * Improves domain expressiveness and ensures type safety when referencing organizations across the system.
 * Internally wraps a {@code Long} value which is still used as the primary key at the persistence layer.
 *
 * @param organizationId the numeric identifier of the organization, must be positive and non-null
 *
 * @since 1.0
 */
@Embeddable
public record OrganizationId(Long organizationId) {

    /**
     * Validates the {@code organizationId} value.
     *
     * @throws IllegalArgumentException if {@code organizationId} is null or less than 1
     */
    public OrganizationId {
        if (organizationId == null || organizationId < 1) {
            throw new IllegalArgumentException("Organization id cannot be null or less than 1");
        }
    }
}
