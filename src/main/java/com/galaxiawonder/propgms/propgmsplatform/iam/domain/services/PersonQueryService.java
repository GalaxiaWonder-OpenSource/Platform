package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByIdQuery;

import java.util.Optional;

/**
 * PersonQueryService
 *
 * @summary
 * Interface that defines query operations related to {@link Person} entities
 * within the IAM context. Follows the CQRS pattern by providing read-only access
 * to person data based on specific query objects.
 *
 * <p>This interface allows other components or bounded contexts to
 * retrieve person information using well-defined query models.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface PersonQueryService {

    /**
     * Handles the retrieval of a {@link Person} entity based on the provided query.
     *
     * @param query the {@link GetPersonByIdQuery} containing the person ID to search for
     * @return the matching {@link Person} entity
     * @since 1.0
     */
    Optional<Person> handle(GetPersonByIdQuery query);

    /**
     * Handles the retrieval of a {@link Person} entity based on the provided query.
     *
     * @param query the {@link GetPersonByEmailQuery} containing the person email to search for
     * @return the matching {@link Person} entity
     * @since 1.0
     */
    Optional<Person> handle(GetPersonByEmailQuery query);
}

