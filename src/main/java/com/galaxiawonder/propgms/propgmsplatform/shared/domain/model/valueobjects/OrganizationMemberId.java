package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import jakarta.persistence.Embeddable;

/**
 * OrganizationMemberId
 *
 * @summary
 * Value object that encapsulates the identifier of an {@link OrganizationMember}.
 * Improves domain expressiveness and ensures type safety when referencing organization members across the system.
 * Internally wraps a {@code Long} value which is still used as the primary key at the persistence layer.
 *
 * @param organizationMemberId the numeric identifier of the organization member, must be positive and non-null
 *
 * @since 1.0
 */
@Embeddable
public record OrganizationMemberId(Long organizationMemberId) {

    /**
     * Validates the {@code organizationMemberId} value.
     *
     * @throws IllegalArgumentException if {@code organizationMemberId} is null or less than 1
     */
    public OrganizationMemberId {
        if (organizationMemberId == null || organizationMemberId < 1) {
            throw new IllegalArgumentException("Organization member id cannot be null or less than 1");
        }
    }
}
