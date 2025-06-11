package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.SignUpResource;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.UserAccountResource;

/**
 * UserAccountResourceFromEntityAssembler
 *
 * @summary
 * Utility class that transforms a {@link UserAccount} domain entity into a {@link UserAccountResource} DTO.
 * It exposes the user-facing representation with selected attributes, abstracting internal structures.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class UserAccountResourceFromEntityAssembler {

    /**
     * Converts a {@link UserAccount} entity into a {@link UserAccountResource}.
     * Maps only public-facing fields such as username and user type.
     *
     * @param entity the domain entity representing the user account
     * @return a {@link UserAccountResource} containing simplified user data
     *
     * @since 1.0
     */
    public static UserAccountResource toResourceFromEntity(UserAccount entity) {
        return new UserAccountResource(
                entity.getUserName().username(),
                entity.getUserType().getStringName()
        );
    }
}


