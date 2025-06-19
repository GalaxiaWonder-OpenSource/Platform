package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonIdByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.EmailAddress;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persistence.jpa.repositories.PersonRepository;
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
    /** Repository for accessing {@link Person} entities from the data store. */
    private final PersonRepository personRepository;

    /**
     * Constructs a new {@code PersonQueryServiceImpl} with the specified repository.
     *
     * @param personRepository the repository used to fetch {@link Person} data
     */
    public PersonQueryServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> handle(GetPersonByIdQuery query) {
        return this.personRepository.findById(query.personId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> handle(GetPersonByEmailQuery query) {
        return this.personRepository.findByEmail(new EmailAddress(query.email()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long handle(GetPersonIdByEmailQuery query) {
        return this.personRepository.findByEmail(new EmailAddress(query.email()))
                .map(AuditableAbstractAggregateRoot::getId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with email: " + query.email()));
    }
}
