package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetAllInvitationsByOrganizationIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetAllMembersByOrganizationIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetAllOrganizationsByMemberPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Optional;

/**
 * OrganizationQueryService
 *
 * @summary
 * This interface represents the service to handle organization source queries.
 */
public interface OrganizationQueryService {
    /**
     * Handles the get organization source by id query
     * @param query the get organization source by id query
     * @return the organization source if exists
     * @throws IllegalArgumentException If id is null or empty
     * @see GetOrganizationByIdQuery
     */
    Optional<Organization> handle(GetOrganizationByIdQuery query);

    /**
     * Handles the query to retrieve all organization invitations associated with a given organization ID.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Fetches all {@link OrganizationInvitation} entities linked to the specified organization.</li>
     *   <li>For each invitation, resolves and includes the {@link ProfileDetails} of the invited person.</li>
     *   <li>Returns the result as a list of {@link ImmutablePair} entries, where the left element is the invitation
     *   and the right element is the corresponding profile details.</li>
     * </ul>
     *
     * @param query the {@link GetAllInvitationsByOrganizationIdQuery} containing the target organization ID
     * @return a {@link List} of {@link ImmutablePair} each containing an {@link OrganizationInvitation} and its associated {@link ProfileDetails}
     *
     * @since 1.0
     */
    List<ImmutablePair<OrganizationInvitation, ProfileDetails>> handle(GetAllInvitationsByOrganizationIdQuery query);

    /**
     * Handles the query to retrieve all organization members associated with a given organization ID.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Fetches all {@link OrganizationMember} entities linked to the specified organization.</li>
     *   <li>For each member, resolves and includes the {@link ProfileDetails} of the associated person.</li>
     *   <li>Returns the result as a list of {@link ImmutablePair} entries, where the left element is the member
     *   and the right element is the corresponding profile details.</li>
     * </ul>
     *
     * @param query the {@link GetAllMembersByOrganizationIdQuery} containing the target organization ID
     * @return a {@link List} of {@link ImmutablePair} each containing an {@link OrganizationMember} and its associated {@link ProfileDetails}
     *
     * @since 1.0
     */
    List<ImmutablePair<OrganizationMember, ProfileDetails>> handle(GetAllMembersByOrganizationIdQuery query);

    /**
     * Handles the query to retrieve all {@link Organization} entities in which
     * the specified person is currently registered as a member.
     *
     * <p>
     * This method performs the following actions:
     * <ul>
     *   <li>Searches for all {@link OrganizationMember} records linked to the given {@code personId}.</li>
     *   <li>Retrieves the corresponding {@link Organization} instances for those memberships.</li>
     *   <li>Returns the complete list of organizations associated with the person.</li>
     * </ul>
     * </p>
     *
     * @param query the {@link GetAllOrganizationsByMemberPersonIdQuery} containing the target person's ID
     * @return a {@link List} of {@link Organization} entities where the person is a member
     *
     * @since 1.0
     */
    List<Organization> handle(GetAllOrganizationsByMemberPersonIdQuery query);
}
