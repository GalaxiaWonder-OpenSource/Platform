package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationMemberTypes;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * OrganizationMember
 *
 * @summary
 * Entity that represents a member within an organization.
 * Each member is associated with a person and an organization, and has a defined role (member type).
 *
 * <p>This entity is created using a {@link CreateOrganizationMemberCommand}, and is intended to be immutable
 * once persisted, except for auditable metadata inherited from {@link AuditableModel}.</p>
 *
 * <p>Typical use cases include adding members to organizations, managing member roles, and querying member lists.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Getter
@Table(name = "organization_members")
@Entity
public class OrganizationMember extends AuditableModel {

    /** Unique identifier of the person associated with this membership. */
    @Column(nullable = false, updatable = false)
    @AttributeOverride(name = "value", column = @Column(name = "person_id"))
    @Embedded
    private PersonId personId;

    /** Unique identifier of the organization this member belongs to. */
    @Column(nullable = false, updatable = false)
    @AttributeOverride(name = "value", column = @Column(name = "organization_id"))
    @Embedded
    private OrganizationId organizationId;

    /** The role or type of the member within the organization (e.g., CONTRACTOR, WORKER). */
    @Column(nullable = false)
    private OrganizationMemberTypes memberType;

    /** Protected default constructor for JPA. */
    protected OrganizationMember() {}

    /**
     * Constructs a new {@link OrganizationMember} based on the provided command.
     *
     * @param command the {@link CreateOrganizationMemberCommand} containing all required values
     *
     * @throws IllegalArgumentException if any of the following is true:
     *         <ul>
     *           <li>{@code command.personId()} is null or blank</li>
     *           <li>{@code command.organizationId()} is null or blank</li>
     *           <li>{@code command.memberType()} is null</li>
     *         </ul>
     */
    public OrganizationMember(CreateOrganizationMemberCommand command) {
        if (command.personId() == null)
            throw new IllegalArgumentException("personId must not be null or blank");
        if (command.organizationId() == null)
            throw new IllegalArgumentException("organizationId must not be null or blank");
        if (command.memberType() == null)
            throw new IllegalArgumentException("memberType must not be null");

        this.personId = new PersonId(command.personId());
        this.organizationId = new OrganizationId(command.organizationId());
        this.memberType = command.memberType();
    }
}
