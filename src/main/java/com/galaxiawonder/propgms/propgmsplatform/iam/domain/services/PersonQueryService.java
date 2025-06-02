package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllPersonsQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByFirstNameQuery;

import java.util.List;
import java.util.Optional;

/**
 * PersonQueryService
 *
 * @summary
 * Service interface that defines query operations related to person data retrieval.
 * It supports fetching a person by first name or retrieving all registered persons.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface PersonQueryService {

    /**
     * Retrieves a person based on their first name.
     *
     * @param getPersonByFirstNameQuery the query containing the first name to search for
     * @return an Optional containing the person if found, or empty if not found
     */
    Optional<Person> handle(GetPersonByFirstNameQuery getPersonByFirstNameQuery);

    /**
     * Retrieves all persons currently stored in the system.
     *
     * @param getAllPersonsQuery the query object for requesting all persons
     * @return a list of all persons
     */
    List<Person> handle(GetAllPersonsQuery getAllPersonsQuery);
}
