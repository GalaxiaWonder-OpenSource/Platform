package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.UserTypeResource;

/**
 * UserTypeResourceFromEntityAssembler
 *
 * @summary
 * Utility class responsible for transforming {@link UserType} entities into {@link UserTypeResource} DTOs.
 * Used to decouple internal domain models from external API representations.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class UserTypeResourceFromEntityAssembler {

    /**
     * Converts a {@link UserType} entity into a {@link UserTypeResource} DTO.
     *
     * @param userType the domain entity to be converted
     * @return a resource representation of the user type
     * @since 1.0
     */
    public static UserTypeResource toResourceFromEntity(UserType userType) {
        return new UserTypeResource(userType.getId(), userType.getStringName());
    }
}
