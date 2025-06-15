package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.*;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMemberType;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationStatus;
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
    /** Repository for performing write operations on {@link Organization} entities. */
    private final OrganizationRepository organizationRepository;

    /** Repository for retrieving and persisting {@link OrganizationStatus} values. */
    private final OrganizationStatusRepository organizationStatusRepository;

    /** Repository for retrieving {@link OrganizationInvitationStatus} values. */
    private final OrganizationInvitationStatusRepository organizationInvitationStatusRepository;

    /** Repository for retrieving {@link OrganizationMemberType} values. */
    private final OrganizationMemberTypeRepository organizationMemberTypeRepository;

    /** Facade to interact with Identity and Access Management context. */
    private final IAMContextFacade iamContextFacade;

    /**
     * Constructs a new {@code OrganizationCommandServiceImpl} with required dependencies.
     *
     * @param organizationRepository the repository for saving and updating organizations
     * @param organizationStatusRepository the repository for fetching organization statuses
     * @param organizationInvitationStatusRepository the repository for invitation statuses
     * @param organizationMemberTypeRepository the repository for member type values
     * @param iamContextFacade the IAM facade for resolving user-related information
     */
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

        OrganizationStatus status = getOrganizationStatus(OrganizationStatuses.ACTIVE);

        OrganizationMemberType contractorType = getOrganizationMemberType(OrganizationMemberTypes.CONTRACTOR);

        var organization = new Organization(command, status, contractorType);

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
     * {@inheritDoc}
     */
    @Transactional
    public Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(InvitePersonToOrganizationByEmailCommand command) {
        var personId = new PersonId(this.iamContextFacade.getPersonIdFromEmail(command.email()));

        Organization organization = this.organizationRepository.findById(command.organizationId())
                .orElseThrow(() -> new EntityNotFoundException("Organization doesn't exist"));

        OrganizationInvitationStatus pendingStatus = getOrganizationInvitationStatus(OrganizationInvitationStatuses.PENDING);

        OrganizationInvitation invitation = organization.addInvitation(personId, pendingStatus);

        saveOrganization(organization);

        return returnInvitationTripleResult(organization, invitation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(AcceptInvitationCommand command) {
        Organization organization = this.organizationRepository.findOrganizationByInvitationId(command.invitationId())
                .orElseThrow(()-> new EntityNotFoundException("No organization found for the given invitation id: " + command.invitationId()));

        OrganizationInvitationStatus acceptedStatus = getOrganizationInvitationStatus(OrganizationInvitationStatuses.ACCEPTED);
        OrganizationMemberType workerType = getOrganizationMemberType(OrganizationMemberTypes.WORKER);

        OrganizationInvitation invitation = organization.acceptInvitation(command.invitationId(), acceptedStatus, workerType);

        saveOrganization(organization);

        return returnInvitationTripleResult(organization, invitation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(RejectInvitationCommand rejectInvitationCommand) {
        Organization organization = this.organizationRepository.findOrganizationByInvitationId(rejectInvitationCommand.invitationId())
                .orElseThrow(() -> new EntityNotFoundException("No organization found for the given invitation id: " + rejectInvitationCommand.invitationId()));

        OrganizationInvitationStatus rejectedStatus = getOrganizationInvitationStatus(OrganizationInvitationStatuses.REJECTED);

        OrganizationInvitation invitation = organization.rejectInvitation(rejectInvitationCommand.invitationId(), rejectedStatus);

        saveOrganization(organization);

        return returnInvitationTripleResult(organization, invitation);
    }

    @Override
    public void handle(DeleteOrganizationMemberCommand command) {
        Organization organization = organizationRepository.findOrganizationByMemberId(command.organizationMemberId())
                .orElseThrow(()-> new IllegalArgumentException("No organization found for the given organization member ID: " + command.organizationMemberId()));

        organization.removeMemberById(command.organizationMemberId());

        saveOrganization(organization);
    }

    private void saveOrganization(Organization organization) {
        organizationRepository.save(organization);
    }

    /**
     * Retrieves the {@link OrganizationStatus} entity matching the given enum value.
     *
     * @param status the {@link OrganizationStatuses} enum representing the desired status
     * @return the corresponding {@link OrganizationStatus} entity
     * @throws IllegalStateException if the status is not found in the repository
     */
    private OrganizationStatus getOrganizationStatus(OrganizationStatuses status) {
        return organizationStatusRepository.findByName(status)
                .orElseThrow(() -> new IllegalStateException("Default status 'ACTIVE' not found"));
    }

    /**
     * Retrieves the {@link OrganizationInvitationStatus} entity matching the given enum value.
     *
     * @param status the {@link OrganizationInvitationStatuses} enum representing the invitation status
     * @return the corresponding {@link OrganizationInvitationStatus} entity
     * @throws IllegalStateException if the status is not found in the repository
     */
    private OrganizationInvitationStatus getOrganizationInvitationStatus(OrganizationInvitationStatuses status) {
        return this.organizationInvitationStatusRepository.findByName(status)
                .orElseThrow(() -> new IllegalStateException("Organization invitation status"));
    }

    /**
     * Retrieves the {@link OrganizationMemberType} entity matching the given enum value.
     *
     * @param status the {@link OrganizationMemberTypes} enum representing the member type
     * @return the corresponding {@link OrganizationMemberType} entity
     * @throws IllegalStateException if the type is not found in the repository
     */
    private OrganizationMemberType getOrganizationMemberType(OrganizationMemberTypes status) {
        return this.organizationMemberTypeRepository.findByName(status)
                .orElseThrow(() -> new IllegalStateException("Organization member type not found"));
    }

    /**
     * Wraps the given organization and invitation along with the creator's profile details
     * into a {@link Triple} and returns it wrapped in an {@link Optional}.
     *
     * @param organization the {@link Organization} entity
     * @param invitation   the {@link OrganizationInvitation} entity
     * @return an {@link Optional} containing a {@link Triple} of organization, invitation and profile details
     */
    private Optional<Triple<Organization, OrganizationInvitation, ProfileDetails>> returnInvitationTripleResult(
            Organization organization, OrganizationInvitation invitation) {

        ProfileDetails profileDetails = getContactorProfileDetails(organization);
        return Optional.of(Triple.of(organization, invitation, profileDetails));
    }

    /**
     * Retrieves the profile details of the given organization's creator..
     *
     * @param organization the {@link Organization} entity
     * @return the {@link ProfileDetails} of the creator
     */
    private ProfileDetails getContactorProfileDetails(Organization organization) {
        return iamContextFacade.getProfileDetailsById(organization.getCreatedBy().personId());
    }
}
