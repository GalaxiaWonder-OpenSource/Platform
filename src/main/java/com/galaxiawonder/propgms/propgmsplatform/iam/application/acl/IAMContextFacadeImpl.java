package com.galaxiawonder.propgms.propgmsplatform.iam.application.acl;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonIdByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IAMContextFacadeImpl implements IAMContextFacade {

    private final PersonQueryService personQueryService;

    public IAMContextFacadeImpl(PersonQueryService personQueryService) {
        this.personQueryService = personQueryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileDetails getProfileDetailsByPersonId(Long personId) {
        var person = this.personQueryService.handle(new GetPersonByIdQuery(personId))
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + personId));

        return new ProfileDetails(
                person.getFirstName(),
                person.getLastName(),
                person.getEmail()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileDetails getProfileDetailsByEmail(String email) {
        var person = this.personQueryService.handle(new GetPersonByEmailQuery(email))
                .orElseThrow(() -> new EntityNotFoundException("Person not found with email: " + email));

        return new ProfileDetails(
                person.getFirstName(),
                person.getLastName(),
                person.getEmail()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileDetails getProfileDetailsById(Long id) {
        var person = this.personQueryService.handle(new GetPersonByIdQuery(id))
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

        return new ProfileDetails(
                person.getFirstName(),
                person.getLastName(),
                person.getEmail()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getPersonIdFromEmail(String email) {
        return this.personQueryService.handle(new GetPersonIdByEmailQuery(email));
    }
}
