package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.*;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMemberType;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationInvitationStatuses;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationMemberTypes;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationStatuses;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationInvitationStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationMemberTypeRepository;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * OrganizationCommandService Implementation
 *
 * @summary
 * Implementation of the OrganizationCommandService interface.
 * It is responsible for handling organization commands.
 */
@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationStatusRepository organizationStatusRepository;
    private final OrganizationInvitationStatusRepository organizationInvitationStatusRepository;
    private final OrganizationMemberTypeRepository organizationMemberTypeRepository;
    private final IAMContextFacade iamContextFacade;

    public OrganizationCommandServiceImpl(
            OrganizationRepository organizationRepository,
            OrganizationStatusRepository organizationStatusRepository,
            OrganizationInvitationStatusRepository organizationInvitationStatusRepository,
            OrganizationMemberTypeRepository organizationMemberTypeRepository,
            IAMContextFacade iamContextFacade
    ) {
        this.organizationRepository = organizationRepository;
        this.organizationStatusRepository = organizationStatusRepository;
        this.iamContextFacade = iamContextFacade;
        this.organizationInvitationStatusRepository = organizationInvitationStatusRepository;
        this.organizationMemberTypeRepository = organizationMemberTypeRepository;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Organization> handle(CreateOrganizationCommand command){
        if(organizationRepository.existsByRuc(new Ruc(command.ruc())))
            throw new IllegalArgumentException("Organization with same RUC already exists for this API key");

        var status = organizationStatusRepository.findByName(OrganizationStatuses.ACTIVE)
                .orElseThrow(() -> new IllegalStateException("Default status 'ACTIVE' not found"));

        var organization = new Organization(command, status);
        var createdOrganization = organizationRepository.save(organization);
        return Optional.of(createdOrganization);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(DeleteOrganizationCommand command) {
        Ruc ruc = new Ruc(command.ruc());
        if (!organizationRepository.existsByRuc(ruc)) {
            throw new IllegalArgumentException("Organization doesn't exist");
        }
        Organization organization = organizationRepository.findByRuc(ruc);
        organizationRepository.delete(organization);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Organization> handle(UpdateOrganizationCommand command) {
        var result = organizationRepository.findById(command.organizationId());
        if (result.isEmpty())
            throw new IllegalArgumentException("Organization doesn't exist");
        var organizationToUpdate = result.get();
        try{
            var updatedOrganization = organizationRepository.save(organizationToUpdate.updateInformation(command.commercialName()));
            return Optional.of(updatedOrganization);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating organization: %s".formatted(e.getMessage()));
        }
    }

    /**
     * Handles the command to invite a person to an organization using their email address.
     *
     * <p>This method:
     * <ul>
     *   <li>Looks up the {@link PersonId} associated with the given email.</li>
     *   <li>Fetches the {@link Organization} by its ID.</li>
     *   <li>Retrieves the {@link OrganizationInvitationStatus} for {@code PENDING}.</li>
     *   <li>Adds a new {@link OrganizationInvitation} to the organization, enforcing business rules.</li>
     *   <li>Persists the organization, triggering a cascade to save the new invitation.</li>
     *   <li>Retrieves profile details of the inviter for response purposes.</li>
     * </ul>
     *
     * @param command the {@link InvitePersonToOrganizationByEmailCommand} containing the email and organization ID
     * @return an {@link Optional} containing a pair with the updated {@link Organization} and the inviter's {@link ProfileDetails}
     * @throws EntityNotFoundException  if the organization does not exist or the profile cannot be found
     * @throws IllegalArgumentException if the person is already a member or already has a pending invitation
     * @since 1.0
     */
    @Transactional
    public Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(InvitePersonToOrganizationByEmailCommand command) {
        var personId = new PersonId(this.iamContextFacade.getPersonIdFromEmail(command.email()));

        Organization organization = this.organizationRepository.findById(command.organizationId())
                .orElseThrow(() -> new EntityNotFoundException("Organization doesn't exist"));

        OrganizationInvitationStatus pendingStatus = getOrganizationInvitationStatus(OrganizationInvitationStatuses.PENDING);

        OrganizationInvitation invitation = organization.addInvitation(personId, pendingStatus);

        organizationRepository.save(organization);

        return returnInvitationTripleResult(organization, invitation);
    }

    /**
     * Handles the command to accept an organization invitation by its ID.
     *
     * <p>This method:
     * <ul>
     *   <li>Fetches the {@link Organization} that owns the invitation using the invitation ID.</li>
     *   <li>Retrieves the {@link OrganizationInvitationStatus} corresponding to {@code ACCEPTED}.</li>
     *   <li>Delegates to the aggregate root to apply the domain logic and accept the invitation.</li>
     *   <li>Persists the updated {@link Organization}, including the accepted invitation.</li>
     *   <li>Returns a pair containing the updated organization and the inviter's {@link ProfileDetails}.</li>
     * </ul>
     *
     * @param command the {@link AcceptInvitationCommand} containing the ID of the invitation to accept
     * @return an {@link Optional} containing a pair of {@link Organization} and {@link ProfileDetails}
     * @throws EntityNotFoundException if the organization or invitation is not found
     * @throws IllegalStateException if the invitation cannot be accepted (e.g., already accepted or rejected)
     *
     * @since 1.0
     */
    @Override
    public Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(AcceptInvitationCommand command) {
        Organization organization = this.organizationRepository.findOrganizationByInvitationId(command.invitationId())
                .orElseThrow(()-> new EntityNotFoundException("No organization found for the given invitation id: " + command.invitationId()));

        OrganizationInvitationStatus acceptedStatus = getOrganizationInvitationStatus(OrganizationInvitationStatuses.ACCEPTED);
        OrganizationMemberType workerType = getOrganizationMemberType(OrganizationMemberTypes.WORKER);

        OrganizationInvitation invitation = organization.acceptInvitation(command.invitationId(), acceptedStatus, workerType);

        organizationRepository.save(organization);

        return returnInvitationTripleResult(organization, invitation);
    }

    /**
     * Handles the command to reject an organization invitation by its ID.
     *
     * <p>This method:
     * <ul>
     *   <li>Fetches the {@link Organization} that owns the invitation using the invitation ID.</li>
     *   <li>Retrieves the {@link OrganizationInvitationStatus} corresponding to {@code REJECTED}.</li>
     *   <li>Delegates to the aggregate root to apply the domain logic and reject the invitation.</li>
     *   <li>Persists the updated {@link Organization}, including the rejected invitation.</li>
     *   <li>Returns a pair containing the updated organization and the inviter's {@link ProfileDetails}.</li>
     * </ul>
     *
     * @param rejectInvitationCommand the {@link RejectInvitationCommand} containing the ID of the invitation to reject
     * @return an {@link Optional} containing a pair of {@link Organization} and {@link ProfileDetails}
     * @throws EntityNotFoundException if the organization or invitation is not found
     * @throws IllegalStateException if the invitation cannot be rejected (e.g., already accepted or rejected)
     *
     * @since 1.0
     */
    @Override
    public Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(RejectInvitationCommand rejectInvitationCommand) {
        Organization organization = this.organizationRepository.findOrganizationByInvitationId(rejectInvitationCommand.invitationId())
                .orElseThrow(() -> new EntityNotFoundException("No organization found for the given invitation id: " + rejectInvitationCommand.invitationId()));

        OrganizationInvitationStatus rejectedStatus = getOrganizationInvitationStatus(OrganizationInvitationStatuses.REJECTED);

        OrganizationInvitation invitation = organization.rejectInvitation(rejectInvitationCommand.invitationId(), rejectedStatus);

        organizationRepository.save(organization);

        return returnInvitationTripleResult(organization, invitation);
    }

    private OrganizationInvitationStatus getOrganizationInvitationStatus(OrganizationInvitationStatuses status) {
        return this.organizationInvitationStatusRepository.findByName(status)
                .orElseThrow(() -> new IllegalStateException("Organization invitation status"));
    }

    private OrganizationMemberType getOrganizationMemberType(OrganizationMemberTypes status) {
        return this.organizationMemberTypeRepository.findByName(status)
                .orElseThrow(() -> new IllegalStateException("Organization member type not found"));
    }

    private Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> returnInvitationTripleResult(
            Organization organization, OrganizationInvitation invitation) {

        ProfileDetails profileDetails = getContactorProfileDetails(organization);

        return Optional.of(Triple.of(organization, invitation, profileDetails));
    }


    private ProfileDetails getContactorProfileDetails(Organization organization) {
        return iamContextFacade.getProfileDetailsById(organization.getCreatedBy().personId());
    }
}
