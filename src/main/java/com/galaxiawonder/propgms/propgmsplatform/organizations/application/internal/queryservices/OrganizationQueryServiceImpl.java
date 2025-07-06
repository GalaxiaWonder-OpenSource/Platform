package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.*;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationQueryService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * OrganizationQueryService Implementation
 *
 * @summary
 * Implementation of the OrganizationQueryService interface.
 * It is responsible for handling organization queries.
 */
@Service
public class OrganizationQueryServiceImpl implements OrganizationQueryService {
    /** Repository for querying {@link Organization} entities from the data source. */
    private final OrganizationRepository organizationRepository;

    /** IAMContext Facade for querying data such as {@link ProfileDetails} */
    private final IAMContextFacade iamContextFacade;

    /**
     * Constructs a new {@code OrganizationQueryServiceImpl} with the given repository.
     *
     * @param organizationRepository the repository used to fetch organization data
     */
    public OrganizationQueryServiceImpl(OrganizationRepository organizationRepository, IAMContextFacade iamContextFacade) {
        this.organizationRepository = organizationRepository;
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Organization> handle(GetOrganizationByIdQuery query){
        return organizationRepository.findById(query.id());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ImmutablePair<OrganizationInvitation, ProfileDetails>> handle(GetAllInvitationsByOrganizationIdQuery query) {
        Organization organization = organizationRepository.findById(query.organizationId())
                .orElseThrow(() -> new IllegalArgumentException("No organization found by the given ID: " + query.organizationId()));

        List<OrganizationInvitation> invitations = new ArrayList<>(organization.getInvitations());

        // Get set of personIds that are already members
        Set<PersonId> members = organization.getMembers().stream()
                .map(OrganizationMember::getPersonId)
                .collect(Collectors.toSet());

        Collections.reverse(invitations); // newest to oldest

        Set<PersonId> seen = new HashSet<>();

        return invitations.stream()
                .filter(inv -> !members.contains(inv.getInvitedPersonId()))
                .filter(inv -> seen.add(inv.getInvitedPersonId()))
                .map(inv -> {
                    ProfileDetails profileDetails = iamContextFacade.getProfileDetailsById(inv.getInvitedPersonId().personId());
                    return ImmutablePair.of(inv, profileDetails);
                })
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrganizationMember> handle(GetAllMembersByOrganizationIdQuery query) {
        Organization organization = organizationRepository.findById(query.organizationId())
                .orElseThrow(() -> new IllegalArgumentException("No organization found by the given ID: " + query.organizationId()));

        return organization.getMembers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> handle(GetAllOrganizationsByMemberPersonIdQuery query) {

        return organizationRepository.findAllOrganizationsByOrganizationMemberPersonId(query.personId())
                .orElseThrow(()-> new IllegalArgumentException("The person with the ID " + query.personId() + " does not belong to any organization"));
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public List<Triple<Organization, OrganizationInvitation, ProfileDetails>> handle(GetAllInvitationsByPersonIdQuery query) {
        Long personId = query.personId();

        List<Organization> organizations = organizationRepository.findAll();

        return organizations.stream()
                .flatMap(org -> org.getInvitations().stream()
                        .filter(invitation ->
                                invitation.getInvitedPersonId().personId().equals(personId)
                                        && invitation.isPending())
                        .map(invitation -> {
                            ProfileDetails profile = iamContextFacade.getProfileDetailsById(org.getCreatedBy().personId());
                            return Triple.of(org, invitation, profile);
                        }))
                .toList();
    }

    @Override
    public Optional<List<Organization>> handle(GetAllOrganizationsQuery query){
        return Optional.of(organizationRepository.findAll());
    }
}
