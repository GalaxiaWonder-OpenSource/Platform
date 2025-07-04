package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.*;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.*;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationQueryService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.assemblers.*;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.*;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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

        var command = new UpdateOrganizationCommand(
                id,
                resource.commercialName() != null ? resource.commercialName() : "",
                resource.legalName() != null ? resource.legalName() : ""
        );

        organizationCommandService.handle(command);
        return ResponseEntity.ok("Organization with given ID successfully updated");
    }

    @Operation(
            summary = "Invite a person to an organization by email",
            description = "Creates a new invitation for a person to join an existing organization, validating business rules and setting the initial status to PENDING."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invitation successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request or person already invited/member"),
            @ApiResponse(responseCode = "404", description = "Organization not found or person profile unavailable")
    })
    @PostMapping("/invitations")
    public ResponseEntity<OrganizationInvitationResource> invitePersonToOrganization(
            @RequestBody InvitePersonToOrganizationResource resource) {

        Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> invitation = organizationCommandService
                .handle(InvitePersonToOrganizationCommandFromResource.toCommandFromResource(resource));

        return buildOrganizationInvitationResource(invitation);
    }

    @Operation(
            summary = "Accept an invitation by ID",
            description = "Accepts a pending organization invitation using its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invitation accepted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state or business rule violation"),
            @ApiResponse(responseCode = "404", description = "Invitation or associated organization not found")
    })
    @PatchMapping("/invitations/{id}/accept")
    public ResponseEntity<OrganizationInvitationResource> acceptInvitation(
            @Parameter(description = "ID of the invitation to accept", required = true)
            @PathVariable Long id) {

        Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> updatedInvitation = organizationCommandService
                .handle(new AcceptInvitationCommand(id));

        return buildOrganizationInvitationResource(updatedInvitation);
    }

    @Operation(
            summary = "Reject an invitation by ID",
            description = "Rejects a pending organization invitation using its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invitation rejected successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state or business rule violation"),
            @ApiResponse(responseCode = "404", description = "Invitation or associated organization not found")
    })
    @PatchMapping("/invitations/{id}/reject")
    public ResponseEntity<OrganizationInvitationResource> rejectInvitation(
            @Parameter(description = "ID of the invitation to reject", required = true)
            @PathVariable Long id) {

        Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> updatedInvitation = organizationCommandService
                .handle(new RejectInvitationCommand(id));

        return buildOrganizationInvitationResource(updatedInvitation);
    }

    private static ResponseEntity<OrganizationInvitationResource> buildOrganizationInvitationResource(
            Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> invitationTriple) {

        return invitationTriple
                .map(triple -> {
                    OrganizationInvitationResource resourceResponse =
                            OrganizationInvitationResourceFromEntityAssembler.toResourceFromEntity(triple);
                    return new ResponseEntity<>(resourceResponse, HttpStatus.CREATED);
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Retrieves all invitations associated with a specific organization.
     *
     * @param organizationId the ID of the organization
     * @return a list of {@link OrganizationInvitationResource} objects
     */
    @Operation(
            summary = "Get all invitations by organization ID",
            description = "Retrieves all invitations associated with the given organization ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invitations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/{organizationId}/invitations")
    public ResponseEntity<List<OrganizationInvitationResource>> getAllInvitationsByOrganizationId(
            @Parameter(description = "ID of the organization", required = true)
            @PathVariable Long organizationId) {

        List<ImmutablePair<OrganizationInvitation, ProfileDetails>> organizationInvitations =
                organizationQueryService.handle(new GetAllInvitationsByOrganizationIdQuery(organizationId));

        List<OrganizationInvitationResource> resources = organizationInvitations.stream()
                .map(OrganizationInvitationResourceFromEntityAssembler::toResourceFromPair)
                .toList();

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * Retrieves all invitations associated with a specific person.
     *
     * @param personId the ID of the person
     * @return a list of {@link OrganizationInvitationResource} objects
     */
    @Operation(
            summary = "Get all invitations by person ID",
            description = "Retrieves all organization invitations with PENDING status associated with the given person ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invitations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No invitations found for the given person ID")
    })
    @GetMapping("/invitations/by-person-id/{personId}")
    public ResponseEntity<List<OrganizationInvitationResource>> getAllInvitationsByPersonId(
            @Parameter(description = "ID of the person", required = true)
            @PathVariable Long personId) {

        List<Triple<Organization, OrganizationInvitation, ProfileDetails>> organizationInvitations =
                organizationQueryService.handle(new GetAllInvitationsByPersonIdQuery(personId));

        List<OrganizationInvitationResource> resources = organizationInvitations.stream()
                .map(OrganizationInvitationResourceFromEntityAssembler::toResourceFromEntity)
                .filter(Objects::nonNull)
                .toList();

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }



    /**
     * Retrieves all members associated with a specific organization.
     *
     * @param organizationId the ID of the organization
     * @return a list of {@link OrganizationMemberResource} objects
     */
    @Operation(
            summary = "Get all members by organization ID",
            description = "Retrieves all active members associated with the given organization ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Members retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/{organizationId}/members")
    public ResponseEntity<List<OrganizationMemberResource>> getAllMembersByOrganizationId(
            @Parameter(description = "ID of the organization", required = true)
            @PathVariable Long organizationId) {

        List<OrganizationMember> organizationMembers =
                organizationQueryService.handle(new GetAllMembersByOrganizationIdQuery(organizationId));

        List<OrganizationMemberResource> resources = organizationMembers.stream()
                .map(OrganizationMemberResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * Deletes an organization member by their unique ID.
     *
     * @param memberId the ID of the member to be removed
     * @return HTTP 204 No Content if successful
     */
    @Operation(
            summary = "Delete a member from the organization",
            description = "Removes a member from the organization by their unique member ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Business rule violation (e.g. trying to delete a CONTRACTOR)"),
            @ApiResponse(responseCode = "404", description = "Member or organization not found")
    })
    @DeleteMapping("/organization-members/{memberId}")
    public ResponseEntity<Void> deleteMemberById(
            @Parameter(description = "ID of the organization member", required = true)
            @PathVariable Long memberId
    ) {
        organizationCommandService.handle(new DeleteOrganizationMemberCommand(memberId));
        return ResponseEntity.noContent().build();
    }


    /**
     * Retrieves all organizations where a given person is a member.
     *
     * @param personId the ID of the person
     * @return a list of {@link OrganizationResource} objects representing the organizations
     */
    @Operation(
            summary = "Get organizations by person ID",
            description = "Retrieves all organizations where the given person is a registered member"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organizations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found or has no memberships")
    })
    @GetMapping("/by-person-id/{id}")
    public ResponseEntity<List<OrganizationResource>> getOrganizationsByPersonId(
            @Parameter(description = "ID of the person", required = true)
            @PathVariable("id") Long personId
    ) {
        List<Organization> organizations = organizationQueryService.handle(
                new GetAllOrganizationsByMemberPersonIdQuery(personId)
        );

        List<OrganizationResource> resources = organizations.stream()
                .map(OrganizationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
