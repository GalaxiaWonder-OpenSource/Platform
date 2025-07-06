package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SignUpCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.SignUpResource;

/**
 * SignUpCommandFromResourceAssembler
 *
 * @summary
 * Utility class responsible for converting a {@link SignUpResource} object into a {@link SignUpCommand}.
 * Encapsulates the mapping logic from the API layer to the domain command layer,
 * ensuring that optional fields like phone are handled gracefully.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class SignUpCommandFromResourceAssembler {

    /**
     * Converts a {@link SignUpResource} into a {@link SignUpCommand}.
     * Optional fields (like phone) are passed as null if not provided.
     *
     * @param resource the incoming API resource containing sign-up data
     * @return a domain command representing the user's sign-up request
     *
     * @since 1.0
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(
                resource.userName(),
                resource.password(),
                resource.userType(),
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.phone() != null ? resource.phone() : null,
                resource.professionalId() != null ? resource.professionalId() : null,
                resource.specialty() != null ? resource.specialty() : null
        );
    }
}