package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.PersonResource;

/**
 * PersonResourceFromEntityAssembler
 *
 * @summary
 * Utility class that transforms a {@code Person} domain entity into a {@code PersonResource} DTO.
 * It maps all the relevant fields, handling optional value objects such as phone and professionalId.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public class PersonResourceFromEntityAssembler {

    /**
     * Converts a {@code Person} domain entity into a {@code PersonResource}.
     * Optional value objects like {@code phone} and {@code professionalId} are included
     * only if they are not null, returning {@code null} otherwise.
     *
     * @param person the domain entity representing the person
     * @return a {@code PersonResource} populated with data from the entity
     */
    public static PersonResource toResourceFromEntity(Person person) {
        return new PersonResource(
                person.getId(),
                person.getFirstname(),
                person.getLastname(),
                person.getEmail().toString(),
                person.getPhone() != null ? person.getPhone().toString() : null,
                person.getProfessionalId() != null ? person.getProfessionalId().toString() : null
        );
    }
}

