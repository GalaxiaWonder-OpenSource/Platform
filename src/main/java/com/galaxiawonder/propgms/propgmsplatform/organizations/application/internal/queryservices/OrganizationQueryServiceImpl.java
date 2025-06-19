package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetAllInvitationsByOrganizationIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetAllMembersByOrganizationIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetAllOrganizationsByMemberPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationQueryService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        List<OrganizationInvitation> organizationInvitations = organization.getInvitations();

        return organizationInvitations.stream()
                .map(invitation -> {
                    ProfileDetails profileDetails = iamContextFacade
                            .getProfileDetailsById(
                                    invitation.getInvitedPersonId().personId()
                            );
                    return ImmutablePair.of(invitation, profileDetails);
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

}
