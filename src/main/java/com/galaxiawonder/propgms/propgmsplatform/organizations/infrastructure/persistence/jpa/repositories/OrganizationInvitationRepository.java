package com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
