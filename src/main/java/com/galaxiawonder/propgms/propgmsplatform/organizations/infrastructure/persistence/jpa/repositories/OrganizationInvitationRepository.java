package com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationInvitationRepository extends JpaRepository<OrganizationInvitation, Long> {

    /**
     * Retrieves all {@link OrganizationInvitation} entities associated with the given invited person ID.
     *
     * <p>This method is intended for read-only access to invitations sent to a specific person,
     * regardless of their organization or status.</p>
     *
     * @param id the unique identifier of the invited person
     * @return a {@link List} of {@link OrganizationInvitation} entities associated with the person
     *
     * @since 1.0
     */
    List<OrganizationInvitation> findAllByInvitedPersonId(Long id);

    /**
     * Retrieves the most recent {@link OrganizationInvitation} for a given organization and invited person.
     *
     * <p>This method uses the organization ID and the {@link PersonId} of the invited person
     * to find the latest invitation entry (based on descending ID order).</p>
     *
     * @param organization_id the ID of the organization to search within
     * @param invitedPersonId the {@link PersonId} of the person who was invited
     * @return an {@link Optional} containing the latest {@link OrganizationInvitation} if found, or empty if none exist
     *
     * @since 1.0
     */
    Optional<OrganizationInvitation> findTopByOrganizationIdAndInvitedPersonIdOrderByIdDesc(
            Long organization_id, PersonId invitedPersonId);
}
