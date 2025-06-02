package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.CreatePersonCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.PersonResource;

/**
 * CreatePersonCommandFromResourceAssembler
 *
 * @summary
 * Assembler utility that transforms a {@code PersonResource} object into a {@code CreatePersonCommand}.
 * This class encapsulates the logic needed to map incoming API resource data into a domain-level command,
 * respecting optional fields like phone and professional ID.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class CreatePersonCommandFromResourceAssembler {

    /**
     * Converts a {@code PersonResource} into a {@code CreatePersonCommand}.
     * Optional fields such as phone and professionalId are handled if null.
     *
     * @param personResource the resource object received from the API layer
     * @return a {@code CreatePersonCommand} containing the corresponding domain data
     */
    public static CreatePersonCommand toCommandFromResource(PersonResource personResource) {
        return new CreatePersonCommand(
                personResource.firstName(),
                personResource.lastName(),
                personResource.email(),
                personResource.phone() != null ? personResource.phone() : null,
                personResource.professionalId() != null ? personResource.professionalId() : null
        );
    }
}

