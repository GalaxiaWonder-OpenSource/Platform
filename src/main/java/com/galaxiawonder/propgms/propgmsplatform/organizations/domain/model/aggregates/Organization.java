package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.InvitePersonToOrganizationByEmailCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitation;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.CommercialName;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.LegalName;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Organization Aggregate Root
 *
 * @summary
 * The Organization class is an aggregate root that represents an organization source.
 * It is responsible for handling the CreateOrganizationCommand command.
 * */
@Entity
@Table(name = "organizations")
@EntityListeners(AuditingEntityListener.class)
public class Organization extends AuditableAbstractAggregateRoot<Organization> {
    @Column(nullable = false)
    @Getter
    @Embedded
    private LegalName legalName;

    @Column()
    @Getter
    @Embedded
    private CommercialName commercialName;

    @Column(nullable = false, updatable = false)
    @Getter
    @Embedded
    private Ruc ruc;

    @Column(nullable = false, updatable = false)
    @Getter
    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    @Embedded
    private PersonId createdBy;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "organization_status_id", nullable = false, unique = false)
    private OrganizationStatus status;

    @Getter
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrganizationMember> members = new ArrayList<>();

    @Getter
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrganizationInvitation> invitations = new ArrayList<>();

    /*
    * Members and invitations will be implemented later.
    * */

    protected Organization() {}

    /**
     * Constructs a new Organization instance by initializing its fields using the provided CreateOrganizationCommand.
     *
     * @param command the command containing the required information to create an Organization,
     *                including legalName, CommercialName, RUC, createdBy, and status.
     *                - legalName must not be null or empty.
     *                - RUC must be valid (exactly 11 digits, starting with '10' or '20').
     *                - createdBy must not be null or empty.
     *                - status must not be null.
     */

    public Organization(CreateOrganizationCommand command, OrganizationStatus status) {
        this.legalName = new LegalName(command.legalName());
        this.commercialName = command.commercialName() != null ? new CommercialName(command.commercialName()) : new CommercialName(""); this.ruc = new Ruc(command.ruc());
        this.createdBy = new PersonId(command.createdBy());
        this.status = status;
    }

    /**
     * Create a new Organization with the given title and description
     * @param commercialName The commercial name of the organization
     */
    public Organization updateInformation(String commercialName){
        this.commercialName = new CommercialName(commercialName);
        return this;
    }

    /**
     * Initiates an invitation process for a person by their {@link PersonId},
     * with a given {@link OrganizationInvitationStatus}.
     *
     * <p>This method ensures business rules are respected:
     * <ul>
     *   <li>The person must not already be a member of the organization.</li>
     *   <li>There must not be an existing pending invitation for the person.</li>
     * </ul>
     * If both checks pass, a new {@link OrganizationInvitation} is created using
     * the provided status and added to the organization.
     *
     * @param personId the ID of the person to be invited
     * @param status   the status to assign to the new invitation (typically {@code PENDING})
     * @throws IllegalArgumentException if the person is already a member
     *                                  or has a pending invitation
     *
     * @since 1.0
     */
    public void addInvitation(PersonId personId, OrganizationInvitationStatus status) {
        if (isAlreadyMember(personId)) {
            throw new IllegalArgumentException("This person is already a member of the organization.");
        }

        if (hasPendingInvitationFor(personId)) {
            throw new IllegalArgumentException("There is already a pending invitation for this person.");
        }

        var invitation = new OrganizationInvitation(this, personId, status);
        invitations.add(invitation);
    }

    /**
     * Checks whether a person is already a member of this organization.
     *
     * @param personId the ID of the person to verify
     * @return true if the person is already a member, false otherwise
     * @since 1.0
     */
    private boolean isAlreadyMember(PersonId personId) {
        return members.stream()
                .anyMatch(member -> member.getPersonId().equals(personId));
    }

    /**
     * Checks if there is already a pending invitation for the given person.
     *
     * This prevents sending duplicate invitations to the same person.
     *
     * @param personId the ID of the person to check
     * @return true if a pending invitation exists for that person, false otherwise
     * @since 1.0
     */
    private boolean hasPendingInvitationFor(PersonId personId) {
        return invitations.stream()
                .anyMatch(inv -> inv.isPending() && personId.equals(inv.getInvitedPersonId()));
    }

    /**
     * Returns the most recently added {@link OrganizationInvitation}.
     *
     * @return the last invitation in the list, or {@code null} if the list is empty
     */
    public OrganizationInvitation getLastInvitation() {
        if (invitations == null || invitations.isEmpty()) {
            return null;
        }
        return invitations.getLast();
    }
}
