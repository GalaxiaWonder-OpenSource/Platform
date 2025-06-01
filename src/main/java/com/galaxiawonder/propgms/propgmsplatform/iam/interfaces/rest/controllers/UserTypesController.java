package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllUserTypesQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.UserTypeQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers.UserTypeResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.UserTypeResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for managing user types.
 *
 * @summary
 * This controller provides an endpoint to retrieve all available user types in the system.
 * It handles HTTP requests under the path /api/v1/user-types and returns JSON responses.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/user-types", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Types", description = "User Type Management Endpoints")
public class UserTypesController {
    private final UserTypeQueryService userTypeQueryService;

    public UserTypesController(UserTypeQueryService userTypeQueryService) {
        this.userTypeQueryService = userTypeQueryService;
    }

    /**
     * Retrieves all registered user types.
     *
     * @summary
     * Returns a list of all user types available in the system.
     * This endpoint can be used to populate dropdowns or validate user role assignments.
     *
     * @return a {@link ResponseEntity} containing a list of {@link UserTypeResource}
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<List<UserTypeResource>> getAllUserTypes() {
        var getAllUserTypesQuery = new GetAllUserTypesQuery();
        var userTypes = userTypeQueryService.handle(getAllUserTypesQuery);
        var userTypeResources = userTypes.stream().map(UserTypeResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userTypeResources);
    }
}
