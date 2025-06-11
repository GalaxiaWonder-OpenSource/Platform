package com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.UserAccountCommandService;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers.SignInCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers.SignInResponseResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers.SignUpCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.assemblers.UserAccountResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.SignInResource;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.SignInResponseResource;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.SignUpResource;
import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.rest.resources.UserAccountResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthenticationController
 *
 * @summary
 * REST controller that manages user authentication-related operations, such as user registration.
 * Exposes endpoints under {@code /api/v1/auth} and returns JSON responses.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "User Sign-Up and Authentication Endpoints")
public class AuthenticationController {

    private final UserAccountCommandService userAccountCommandService;

    /**
     * Constructs the {@code AuthenticationController} with required dependencies.
     *
     * @param userAccountCommandService service responsible for handling user account creation
     */
    AuthenticationController(UserAccountCommandService userAccountCommandService) {
        this.userAccountCommandService = userAccountCommandService;
    }

    /**
     * Handles the sign-up of a new user in the system.
     *
     * @param signUpResource the request body containing user credentials and personal data
     * @return a {@link ResponseEntity} containing a {@link UserAccountResource} and HTTP status {@code 201 CREATED}
     *         if successful; HTTP status {@code 400 BAD REQUEST} otherwise
     *
     * @since 1.0
     */
    @PostMapping("/signup")
    public ResponseEntity<UserAccountResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var person = userAccountCommandService.handle(signUpCommand);
        if (person.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var resource = UserAccountResourceFromEntityAssembler.toResourceFromEntity(person.get());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Handles the sign-in request for an existing user account.
     * <p>
     *     This endpoint verifies credentials and returns a {@link SignInResponseResource}
     *     containing the user information and a valid JWT token if authentication succeeds.
     * </p>
     *
     * @param signInResource the sign-in data containing username and password
     * @return {@code 200 OK} with the token and user info if successful,
     *         {@code 401 Unauthorized} if authentication fails
     */
    @PostMapping("/signin")
    public ResponseEntity<SignInResponseResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var result = userAccountCommandService.handle(signInCommand);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var responseBody = SignInResponseResourceFromEntityAssembler.toResourceFromEntity(result.get());

        return ResponseEntity.ok(responseBody);
    }

}