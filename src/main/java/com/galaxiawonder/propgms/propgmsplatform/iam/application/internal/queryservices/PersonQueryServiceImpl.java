package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllPersonsQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByFirstNameQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonQueryServiceImpl implements PersonQueryService {
    private final PersonRepository personRepository;

    public PersonQueryServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Handles the query to retrieve all user types.
     *
     * @summary
     * Fetches all user types stored in the repository.
     *
     * @param query the query object (no parameters required)
     * @return a list of {@link UserType} entities
     * @since 1.0
     */
    @Override
    public List<Person> handle(GetAllPersonsQuery query) {
        return personRepository.findAll();
    }

    /**
     * Handles the query to retrieve a user type by name.
     *
     * @summary
     * Finds a user type that matches the specified name, if it exists.
     *
     * @param query the query object containing the user type name to search for
     * @return an {@link Optional} containing the {@link UserType}, or empty if not found
     * @since 1.0
     */
    @Override
    public Optional<Person> handle(GetPersonByFirstNameQuery query) {
        return personRepository.findByFirstname(query.firstName());
    }
}