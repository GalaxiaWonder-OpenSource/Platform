package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.CommercialName;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.LegalName;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

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
    @Column(nullable = false)
    private OrganizationStatus status;

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

    public Organization(CreateOrganizationCommand command) {
        this.legalName = new LegalName(command.legalName());
        this.commercialName = command.commercialName() != null ? new CommercialName(command.commercialName()) : new CommercialName(""); this.ruc = new Ruc(command.ruc());
        this.createdBy = new PersonId(command.createdBy());
        this.status = OrganizationStatus.ACTIVE;
    }
}
