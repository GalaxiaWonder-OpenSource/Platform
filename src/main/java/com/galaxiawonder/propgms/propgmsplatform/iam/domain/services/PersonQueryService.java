package com.galaxiawonder.propgms.propgmsplatform.iam.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllPersonsQuery;

import java.util.List;

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
     * Retrieves all persons currently stored in the system.
     *
     * @param getAllPersonsQuery the query object for requesting all persons
     * @return a list of all persons
     */
    List<Person> handle(GetAllPersonsQuery getAllPersonsQuery);
}
