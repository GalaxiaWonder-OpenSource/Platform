package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllPersonsQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}