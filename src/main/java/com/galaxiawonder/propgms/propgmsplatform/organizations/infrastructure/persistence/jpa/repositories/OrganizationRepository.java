package com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    /**
     * Check if an organization exists by ruc
     * @param ruc RUC
     * @return True if exists, false otherwise
     */
    boolean existsByRuc(Ruc ruc);
    /**
     * Find an organization by ruc
     * @param ruc RUC
     * @return an Organization
     */
    Organization findByRuc(Ruc ruc);
    /**
     * Find an organization by its ID
     * @param id organization's ID
     * @return an Organization
     */
    Optional<Organization> findById(Long id);

    /**
     * Find an organization by using an invitation's ID
     * @param invitationId invitation's ID
     * @return the Organization that contains the invitation with the given ID
     */
    @Query("""
    SELECT i.organization
    FROM OrganizationInvitation i
    WHERE i.id = :invitationId
""")
    Optional<Organization> findOrganizationByInvitationId(@Param("invitationId") Long invitationId);

    /**
     * Finds an organization by using a member's ID.
     *
     * @param memberId the ID of the organization member
     * @return the {@link Organization} that contains the member with the given ID
     */
    @Query("""
    SELECT m.organization
    FROM OrganizationMember m
    WHERE m.id = :memberId
""")
    Optional<Organization> findOrganizationByMemberId(@Param("memberId") Long memberId);

    /**
     * Retrieves all {@link Organization} entities where the given person is registered as a member.
     *
     * @param personId the ID of the person
     * @return a list of organizations where the person is a member
     */
    @Query("""
    SELECT o
    FROM Organization o
    JOIN o.members m
    WHERE m.personId.personId = :personId
""")
    Optional<List<Organization>> findAllOrganizationsByOrganizationMemberPersonId(@Param("personId") Long personId);

}
