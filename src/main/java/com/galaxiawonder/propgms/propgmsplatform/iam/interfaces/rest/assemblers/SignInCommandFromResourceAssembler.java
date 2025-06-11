package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SignInCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.SignInResource;

/**
 * SignInCommandFromResourceAssembler
 *
 * @summary
 * Utility class that transforms a {@link SignInResource} into a {@link SignInCommand}.
 * Used to map incoming API resource data into a domain-level command for user authentication.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class SignInCommandFromResourceAssembler {

    /**
     * Converts a {@link SignInResource} into a {@link SignInCommand}.
     *
     * @param resource the incoming resource object from the API layer
     * @return a {@code SignInCommand} populated with data from the resource
     */
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(
                resource.userName(),
                resource.password()
        );
    }
}

