package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

/**
 * ProfileDetails
 *
 * @summary
 * Read model representing basic personal profile information.
 * This record is commonly used to expose user identity data such as name and email
 * across bounded contexts without exposing internal domain structures.
 *
 * <p>Typical use cases include:</p>
 * <ul>
 *   <li>Displaying member or user identity in views</li>
 *   <li>Enriching other domain models (e.g., organization members)</li>
 * </ul>
 *
 * @param firstName the person's given name
 * @param lastName the person's family name
 * @param email the person's email address
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public record ProfileDetails(
        String firstName,
        String lastName,
        String email
) {}

