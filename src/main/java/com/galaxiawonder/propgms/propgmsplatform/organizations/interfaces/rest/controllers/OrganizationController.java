package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationQueryService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers.CreateOrganizationCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers.OrganizationResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.CreateOrganizationResource;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.OrganizationResource;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.UpdateOrganizationResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST Controller for Organizations.
 * @summary
 * This class provides REST Endpoints for Organizations.
 */
@RestController
@RequestMapping(value="/api/v1/organizations", produces = APPLICATION_JSON_VALUE)
@Tag(name="Organizations", description="Endpoints for Organizations")
public class OrganizationController {
    private final OrganizationCommandService organizationCommandService;
    private final OrganizationQueryService organizationQueryService;

    /**
     * Constructor for OrganizationController.
     * @param organizationCommandService Organization command service
     * @param organizationQueryService Organization query service}
     * @see OrganizationCommandService
     * @see OrganizationQueryService
     */
    public OrganizationController(OrganizationCommandService organizationCommandService, OrganizationQueryService organizationQueryService) {
        this.organizationCommandService = organizationCommandService;
        this.organizationQueryService = organizationQueryService;
    }

    /**
     * Creates an Organization
     * @param resource CreateOrganizationResource containing the required params
     * @return ResponseEntity with the created organization resource, or bad request if the resource is invalid
     * @see CreateOrganizationResource
     * @see OrganizationResource
     */
    @Operation(
            summary = "Create a Organization",
            description = "Creates a Organization with the provided params")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Organization created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<OrganizationResource>
    createOrganization(@RequestBody CreateOrganizationResource resource){
        Optional<Organization> organization = organizationCommandService
                .handle(CreateOrganizationCommandFromResourceAssembler.toCommandFromResource(resource));
        return organization.map(source -> new ResponseEntity<>(OrganizationResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Gets an Organization by ID.
     * @param id Organization ID
     * @return ResponseEntity with the Organization resource if found, or not found otherwise
     * @see OrganizationResource
     */
    @Operation(
            summary = "Get an Organization by ID",
            description = "Gets an Organization by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization found"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<OrganizationResource>
    getOrganizationById(@PathVariable Long id){
        Optional<Organization> organization =
                organizationQueryService.handle(new GetOrganizationByIdQuery(id));
        return organization.map( source ->
                ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(source)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete an Organization by ruc",
            description = "Delete an Organization by the provided ruc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization deleted"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @DeleteMapping("{ruc}")
    public ResponseEntity<?> deleteOrganization(
            @Parameter(description = "RUC")
            @PathVariable String ruc) {
        var deleteOrganizationCommand = new DeleteOrganizationCommand(ruc);
        organizationCommandService.handle(deleteOrganizationCommand);
        return ResponseEntity.ok("Organization with given RUC successfully deleted");
    }
    @PatchMapping("{id}")
    public ResponseEntity<?> updateOrganization(
            @PathVariable Long id,
            @RequestBody UpdateOrganizationResource resource) {

        var command = new UpdateOrganizationCommand(id, resource.commercialName());
        organizationCommandService.handle(command);
        return ResponseEntity.ok("Organization with given ID successfully updated");
    }
}
