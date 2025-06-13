package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonIdByEmailQuery;

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

    /**
     * Handles the retrieval of a person's unique identifier using their email address.
     *
     * <p>This query is typically used in cross-context interactions (e.g., resolving
     * identities for invitations or memberships) where only the email is known.</p>
     *
     * @param query the {@link GetPersonIdByEmailQuery} containing the email address to search for
     * @return the unique identifier of the person as a {@link Long}
     *
     * @since 1.0
     */
    Long handle(GetPersonIdByEmailQuery query);
}

