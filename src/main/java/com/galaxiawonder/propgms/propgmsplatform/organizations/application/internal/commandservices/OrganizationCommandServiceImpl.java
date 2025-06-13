package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.InvitePersonToOrganizationByEmailCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationInvitationStatuses;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationStatuses;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationInvitationStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
    private final IAMContextFacade iamContextFacade;

    public OrganizationCommandServiceImpl(
            OrganizationRepository organizationRepository,
            OrganizationStatusRepository organizationStatusRepository,
            OrganizationInvitationStatusRepository organizationInvitationStatusRepository,
            IAMContextFacade iamContextFacade
    ) {
        this.organizationRepository = organizationRepository;
        this.organizationStatusRepository = organizationStatusRepository;
        this.iamContextFacade = iamContextFacade;
        this.organizationInvitationStatusRepository = organizationInvitationStatusRepository;
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
     * @throws EntityNotFoundException if the organization does not exist or the profile cannot be found
     * @throws IllegalArgumentException if the person is already a member or already has a pending invitation
     *
     * @since 1.0
     */
    @Transactional
    public Optional<ImmutablePair<Organization, ProfileDetails>> handle(InvitePersonToOrganizationByEmailCommand command) {
        var personId = new PersonId(this.iamContextFacade.getPersonIdFromEmail(command.email()));

        Organization organization = this.organizationRepository.findById(command.organizationId())
                .orElseThrow(() -> new EntityNotFoundException("Organization doesn't exist"));

        OrganizationInvitationStatus pendingStatus = this.organizationInvitationStatusRepository.findByName(OrganizationInvitationStatuses.PENDING)
                .orElseThrow(() -> new IllegalStateException("Default status 'PENDING' not found"));

        organization.addInvitation(personId, pendingStatus);

        organizationRepository.save(organization);

        ProfileDetails profileDetails = iamContextFacade.getProfileDetailsById(organization.getCreatedBy().personId());

        return Optional.of(ImmutablePair.of(organization, profileDetails));
    }
}
