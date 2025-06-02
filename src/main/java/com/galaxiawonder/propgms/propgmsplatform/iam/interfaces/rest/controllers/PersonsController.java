package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllPersonsQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonCommandService;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.PersonQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers.CreatePersonCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers.PersonResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.PersonResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing persons.
 *
 * @summary
 * This controller provides endpoints to manage {@code Person} entities, including retrieval and creation.
 * It handles HTTP requests under the base path {@code /api/v1/persons} and produces JSON responses.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/persons", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Persons", description = "Persons Management Endpoints")
public class PersonsController {

    private final PersonQueryService personQueryService;
    private final PersonCommandService personCommandService;

    /**
     * Constructs the {@code PersonsController} with required services.
     *
     * @param personQueryService service for handling read operations on persons
     * @param personCommandService service for handling write operations on persons
     */
    PersonsController(PersonQueryService personQueryService, PersonCommandService personCommandService) {
        this.personQueryService = personQueryService;
        this.personCommandService = personCommandService;
    }

    /**
     * Retrieves all persons registered in the system.
     *
     * @return a list of {@code PersonResource} wrapped in {@code ResponseEntity} with status 200 OK
     */
    @GetMapping
    public ResponseEntity<List<PersonResource>> getAllPersons() {
        var getAllPersonsQuery = new GetAllPersonsQuery();
        var persons = personQueryService.handle(getAllPersonsQuery);
        var personResources = persons.stream().map(PersonResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(personResources);
    }

    /**
     * Creates a new person in the system based on the request body.
     *
     * @param personResource the data needed to create the person
     * @return a {@code PersonResource} with status 201 Created, or 400 Bad Request if creation fails
     */
    @PostMapping
    public ResponseEntity<PersonResource> postPerson(@RequestBody PersonResource personResource) {
        var createPersonCommand = CreatePersonCommandFromResourceAssembler.toCommandFromResource(personResource);
        var person = personCommandService.handle(createPersonCommand);
        if(person.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var WA = PersonResourceFromEntityAssembler.toResourceFromEntity(person.get());
        return new ResponseEntity<>(WA, HttpStatus.CREATED);
    }
}