package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationSourceCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * OrganizationSource Aggregate Root
 *
 * @summary
 * The OrganizationSource class is an aggregate root that represents an organization source.
 * It is responsible for handling the CreateOrganizationSourceCommand command.
 * */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class OrganizationSource extends AbstractAggregateRoot<OrganizationSource> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    @Getter
    private String legalName;

    @Column()
    @Getter
    private String commercialName;

    @Column(nullable = false, updatable = false)
    @Getter
    @Embedded
    private Ruc ruc;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    @Column(nullable = false)
    private OrganizationStatus status;

    /*
    * Members and invitations will be implemented later.
    * */

    protected OrganizationSource() {}

    /**
     * Constructs a new OrganizationSource instance by initializing its fields using the provided CreateOrganizationSourceCommand.
     *
     * @param command the command containing the required information to create an OrganizationSource,
     *                including legalName, commercialName, RUC, createdBy, and status.
     *                - legalName must not be null or empty.
     *                - RUC must be valid (exactly 11 digits, starting with '10' or '20').
     *                - createdBy must not be null or empty.
     *                - status must not be null.
     */

    public OrganizationSource(CreateOrganizationSourceCommand command) {
        this.legalName = command.legalName();
        this.commercialName = command.commercialName();
        this.ruc = command.ruc();
        this.createdBy = command.createdBy();
        this.status = command.status();
    }
}
