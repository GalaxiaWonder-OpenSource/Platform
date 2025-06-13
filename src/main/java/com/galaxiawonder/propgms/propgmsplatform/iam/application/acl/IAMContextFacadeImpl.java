package com.galaxiawonder.propgms.propgmsplatform.iam.application.acl;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.Person;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByEmailQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetPersonByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.acl.IAMContextFacade;
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
     * Retrieves basic profile information of a person using their unique identifier.
     *
     * <p>This method delegates the query to {@link PersonQueryService} and maps the resulting
     * {@link Person} entity to a {@link ProfileDetails} read model.</p>
     *
     * @param personId the ID of the person to retrieve
     * @return a {@link ProfileDetails} object containing the person's first name, last name and email
     * @throws EntityNotFoundException if no person exists with the given ID
     *
     * @since 1.0
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
     * Retrieves basic profile information of a person using their email address.
     *
     * <p>This method delegates the query to {@link PersonQueryService} and maps the resulting
     * {@link Person} entity to a {@link ProfileDetails} read model.</p>
     *
     * @param email the email address of the person to retrieve
     * @return a {@link ProfileDetails} object containing the person's first name, last name and email
     * @throws EntityNotFoundException if no person exists with the given email
     *
     * @since 1.0
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
}
