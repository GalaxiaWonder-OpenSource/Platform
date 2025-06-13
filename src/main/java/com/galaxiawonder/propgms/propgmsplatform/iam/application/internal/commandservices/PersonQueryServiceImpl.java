package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonIdByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.EmailAddress;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.PersonRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * PersonQueryServiceImpl
 *
 * @summary
 * Implementation of the {@link PersonQueryService} that handles queries
 * related to {@link Person} entities within the IAM context.
 *
 * <p>This service supports lookup operations based on person ID or email address,
 * and is responsible for exposing identity-related information as part of the
 * read side of the CQRS pattern.</p>
 *
 * <p>It delegates persistence operations to the {@link PersonRepository} and
 * returns results wrapped in {@link Optional} where appropriate, or throws an
 * {@link EntityNotFoundException} when necessary.</p>
 *
 * <p>Queries supported:</p>
 * <ul>
 *   <li>{@link GetPersonByIdQuery}</li>
 *   <li>{@link GetPersonByEmailQuery}</li>
 *   <li>{@link GetPersonIdByEmailQuery}</li>
 * </ul>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
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

    @Override
    public Long handle(GetPersonIdByEmailQuery query) {
        return this.personRepository.findByEmail(new EmailAddress(query.email()))
                .map(AuditableAbstractAggregateRoot::getId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with email: " + query.email()));
    }
}
