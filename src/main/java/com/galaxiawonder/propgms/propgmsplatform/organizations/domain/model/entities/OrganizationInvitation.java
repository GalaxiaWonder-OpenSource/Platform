package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationInvitationStatuses;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrganizationInvitation extends AuditableModel {

    /**
     * Unique identifier of the organization this invitation belongs to.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    /**
     * Embedded value object representing the invited person.
     */
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "person_id", nullable = false, updatable = false))
    private PersonId invitedPersonId;

    /**
     * Current status of the invitation (e.g., PENDING, ACCEPTED, REJECTED).
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_id", nullable = false, unique = false)
    private OrganizationInvitationStatus status;

    /**
     * Creates a new invitation for the given person to join the specified organization,
     * with a predefined {@link OrganizationInvitationStatus}.
     *
     * <p>This constructor is typically used when the invitation status is retrieved
     * from a fixed set of values (e.g., PENDING, ACCEPTED, REJECTED) already persisted
     * in the database.</p>
     *
     * @param organization      the target organization that sends the invitation
     * @param invitedPersonId   the unique identifier of the invited person
     * @param status            the current status of the invitation
     *
     * @since 1.0
     */
    public OrganizationInvitation(Organization organization, PersonId invitedPersonId, OrganizationInvitationStatus status) {
        this.organization = organization;
        this.invitedPersonId = invitedPersonId;
        this.status = status;
    }

    /**
     * Marks this invitation as accepted.
     */
    public void accept(OrganizationInvitationStatus acceptedStatus) {
        this.status = acceptedStatus;
    }

    /**
     * Marks this invitation as rejected.
     */
    public void reject(OrganizationInvitationStatus rejectedStatus) {
        this.status = rejectedStatus;
    }

    /**
     * Checks if the invitation is still pending.
     *
     * @return true if the status is PENDING
     */
    public boolean isPending() {
        return this.status.getName().name().equals("PENDING");
    }
}
