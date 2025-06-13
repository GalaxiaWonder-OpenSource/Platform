package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.EmailAddress;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonQueryServiceImpl implements PersonQueryService {
    private final PersonRepository personRepository;

    PersonQueryServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> handle(GetPersonByIdQuery query) {
        return this.personRepository.findById(query.personId());
    }

    @Override
    public Optional<Person> handle(GetPersonByEmailQuery query) {
        return this.personRepository.findByEmail(new EmailAddress(query.email()));
    }
}
