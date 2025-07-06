package com.galaxiawonder.propgms.propgmsplatform.organizations.application.acl;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetAllOrganizationsQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationQueryService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.acl.OrganizationContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OrganizationContextFacadeImpl
 *
 * @summary
 * Implementation of the {@link OrganizationContextFacade} that provides access
 * to organization-level contextual data such as contractor and member identification.
 *
 * <p>
 * This facade encapsulates read-only queries used by other bounded contexts
 * (e.g., projects) to resolve cross-context relationships without exposing
 * full domain models or services.
 * </p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class OrganizationContextFacadeImpl implements OrganizationContextFacade {

    /** Service used to query organizations from the domain model. */
    private final OrganizationQueryService organizationQueryService;
    private final OrganizationCommandService organizationCommandService;

    /**
     * Constructs the context facade with its required dependencies.
     *
     * @param organizationQueryService   the service used to retrieve organization aggregates
     * @param organizationCommandService the service used to modify organization aggregates
     */
    public OrganizationContextFacadeImpl(OrganizationQueryService organizationQueryService,
                                         OrganizationCommandService organizationCommandService) {
        this.organizationQueryService = organizationQueryService;
        this.organizationCommandService = organizationCommandService;
    }

    /**
     * Retrieves the ID of the contractor (creator) of a given organization.
     *
     * @param organizationId the ID of the organization
     * @return the {@code personId} of the user who created the organization
     * @throws IllegalArgumentException if the organization does not exist
     */
    @Override
    public Long getContractorIdFromOrganizationId(Long organizationId) {
        Organization organization = this.organizationQueryService.handle(new GetOrganizationByIdQuery(organizationId))
                .orElseThrow(() -> new IllegalArgumentException("No organization found for the ID: " + organizationId));
        return organization.getCreatedBy().personId();
    }

    /**
     * Retrieves the ID of the {@link com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember}
     * that corresponds to a given {@code personId} within a specific organization.
     *
     * @param personId the ID of the person
     * @param organizationId the ID of the organization
     * @return the unique ID of the matching organization member
     * @throws IllegalArgumentException if the organization or member is not found
     */
    @Override
    public Long getOrganizationMemberIdFromPersonAndOrganizationId(Long personId, Long organizationId) {
        Organization organization = this.organizationQueryService.handle(new GetOrganizationByIdQuery(organizationId))
                .orElseThrow(() -> new IllegalArgumentException("No organization found for the ID: " + organizationId));

        return organization.getMembers().stream()
                .filter(member -> member.getPersonId().personId().equals(personId))
                .findFirst()
                .map(AuditableModel::getId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No member found with person ID " + personId + " in organization ID " + organizationId
                ));
    }

    /**
     * Retrieves the person ID associated with a given organization member ID.
     *
     * @param organizationMemberId the ID of the organization member
     * @return the person ID linked to the organization member
     * @throws IllegalArgumentException if no organization member is found with the given ID
     */
    public Long getPersonIdFromOrganizationMemberId(Long organizationMemberId) {
        return this.organizationQueryService
                .handle(new GetAllOrganizationsQuery())
                .stream()
                .flatMap(List::stream)
                .flatMap(org -> org.getMembers().stream())
                .filter(member -> member.getId().equals(organizationMemberId))
                .findFirst()
                .map(member -> member.getPersonId().personId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No organization member found for the ID: " + organizationMemberId
                ));
    }



}

