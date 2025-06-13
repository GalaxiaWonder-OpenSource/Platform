package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
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
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    /** The role or type of the member within the organization (e.g., CONTRACTOR, WORKER). */
    @Column(nullable = false)
    private OrganizationMemberTypes memberType;

    /** Protected default constructor for JPA. */
    protected OrganizationMember() {}
}
