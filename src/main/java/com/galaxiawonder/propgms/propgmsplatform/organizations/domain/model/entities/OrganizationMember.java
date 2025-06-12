package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationMemberTypes;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Organization Member Entity
 *
 * @summary
 * The organization member class is an entity that represents an organization member.
 * It is responsible for handling the CreateOrganizationMemberCommand command.
 * */
@Getter
@Table(name = "organization_members")
@Entity
public class OrganizationMember extends AuditableModel {
    @Column(nullable = false, updatable = false)
    @Getter
    @AttributeOverride(name = "value", column = @Column(name = "person_id"))
    @Embedded
    private PersonId personId;

    @Column(nullable = false, updatable = false)
    @Getter
    @AttributeOverride(name = "value", column = @Column(name = "organization_id"))
    @Embedded
    private OrganizationId organizationId;

    @Getter
    @Column(nullable = false)
    private OrganizationMemberTypes memberType;

    protected OrganizationMember() {}

    /**
     * Constructs a new Organization Member instance by initializing its fields using the provided CreateOrganizationMemberCommand.
     * @param command the command containing the required information to create an Organization Member.
     *                - personId must not be null or zero
     *                - organizationId must not be null or zero
     *                - memberType must not be null
     */
    public OrganizationMember(CreateOrganizationMemberCommand command){
        this.personId = new PersonId(command.personId());
        this.organizationId = new OrganizationId(command.organizationId());
        this.memberType = command.memberType();
    }
}
