package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectTeamMemberType;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.Specialty;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ProjectTeamMember
 *
 * @summary
 * Aggregate root that represents the assignment of an organization member to a project.
 * It encapsulates the role of a person (organization member) within the context
 * of a specific project, including their specialty and identification details.
 *
 * Each team member is uniquely identified by the combination of a project,
 * an organization member, and the person involved.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
public class ProjectTeamMember extends AuditableAbstractAggregateRoot<ProjectTeamMember> {

    /** Identifier of the project to which this member is assigned. */
    @Column(nullable = false, updatable = false)
    @AttributeOverride(name = "description", column = @Column(name = "project_id"))
    @Embedded
    private ProjectId projectId;

    /** Specialty or expertise area of the assigned team member. */
    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "specialty_id", nullable = false, unique = false)
    private Specialty specialty;

    /** Identifier of the organization member assigned to this project. */
    @Column(nullable = false, updatable = false)
    @AttributeOverride(name = "description", column = @Column(name = "organization_member_id"))
    @Embedded
    private OrganizationMemberId organizationMemberId;

    /** Unique identifier of the person associated with this team membership. */
    @Column(nullable = false, updatable = false)
    @AttributeOverride(name = "description", column = @Column(name = "person_id"))
    @Embedded
    private PersonId personId;

    /** Full name of the organization member, encapsulated in a value object. */
    @Getter
    @Embedded
    private PersonName name;

    /** Unique email of the organization member, represented as a value object. */
    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email"))})
    private EmailAddress email;

    /**
     * Current type of project team member (COORDINATOR / SPECIALIST)
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "member_type_id", nullable = false)
    @Getter
    private ProjectTeamMemberType memberType;

    /**
     * Creates a new {@link ProjectTeamMember} with the given identifiers and personal information.
     *
     * @param projectId the project to which the member is assigned
     * @param specialty the specialty or role of the member in the project
     * @param organizationMemberId the ID of the organization member
     * @param personId the unique identifier of the person
     * @param profileDetails contains name and email
     */
    public ProjectTeamMember(
            ProjectId projectId,
            PersonId personId,
            OrganizationMemberId organizationMemberId,
            ProfileDetails profileDetails,
            Specialty specialty

    ) {
        this.projectId = projectId;
        this.specialty = specialty;
        this.organizationMemberId = organizationMemberId;
        this.personId = personId;
        this.name = profileDetails.name();
        this.email = profileDetails.email();
    }
}

