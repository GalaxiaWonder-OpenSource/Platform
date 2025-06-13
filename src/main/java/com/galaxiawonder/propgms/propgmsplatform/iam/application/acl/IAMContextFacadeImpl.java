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

    /**
     * Retrieves basic profile information of a person using their internal system ID.
     *
     * <p>This method delegates the query to {@link PersonQueryService} and maps the resulting
     * {@link Person} entity to a {@link ProfileDetails} read model.</p>
     *
     * @param id the internal ID of the person to retrieve
     * @return a {@link ProfileDetails} object containing the person's first name, last name and email
     * @throws EntityNotFoundException if no person exists with the given ID
     *
     * @since 1.0
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
     * Retrieves the unique identifier of a person based on their email address.
     *
     * <p>This method delegates the query to the {@link PersonQueryService} and returns
     * the corresponding person ID if the email exists in the system.</p>
     *
     * @param email the email address of the person to be resolved
     * @return the person's unique identifier as a {@link Long}
     * @throws EntityNotFoundException if no person exists with the given email
     *
     * @since 1.0
     */
    public Long getPersonIdFromEmail(String email) {
        return this.personQueryService.handle(new GetPersonIdByEmailQuery(email));
    }
}
