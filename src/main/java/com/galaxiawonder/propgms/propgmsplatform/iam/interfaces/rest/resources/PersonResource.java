package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources;

import jakarta.annotation.Nullable;

/**
 * PersonResource
 *
 * @summary
 * Represents the resource for a {@code Person} entity exposed through the API.
 * It encapsulates essential personal information such as name, email, and optionally a phone number
 * or professional ID from an external registry (e.g., CAP or CIP).
 *
 * This class is used for both incoming (POST) and outgoing (GET) data representations.
 *
 * @param id the unique identifier of the person (nullable when creating a new record)
 * @param firstName the person's first name (required)
 * @param lastName the person's last name (required)
 * @param email the person's email address (required)
 * @param phone the person's phone number, optional at creation
 * @param professionalId the professional ID of the person, optional at creation
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record PersonResource(
        Long id,
        String firstName,
        String lastName,
        String email,
        @Nullable String phone,
        @Nullable String professionalId
) {
}

