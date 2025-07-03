package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.events.ProjectCreatedEvent;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project
 *
 * @summary
 * Aggregate root that represents a project within the system.
 * A project has a projectName, description, schedule (with milestones),
 * and a current status that reflects its lifecycle stage.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
public class Project extends AuditableAbstractAggregateRoot<Project> {

    /** Name of the project, encapsulated in a description object. */
    @Column(nullable = false)
    @Getter
    @Embedded
    private ProjectName projectName;

    /** Description of the project, encapsulated in a description object. */
    @Column
    @Getter
    @Embedded
    private Description description;

    /** Range of dates within the project will be done. */
    @Column(nullable = false)
    @Getter
    @Embedded
    private DateRange dateRange;

    /** Identifier of the organization responsible for the project. */
    @Column(nullable = false)
    @Getter
    @Embedded
    private OrganizationId organizationId;

    /** Identifier of the person or entity in charge of contracting. */
    @Column(nullable = false)
    @Getter
    @Embedded
    private PersonId contractingEntityId;

    /** Current status of the project, represented as an entity. */
    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_id", nullable = false, unique = false)
    private ProjectStatus status;

    @Getter
    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProjectTeamMember> teamMembers = new ArrayList<>();

    /** Default constructor required by JPA. */
    public Project() {}

    /**
     * Constructs a project with the required fields.
     * @param command Contains projectName (max 30 characters), description (max 200 characters), and dateRange (startDate cannot be after endDate)
     * @param status The current status of the project
     * @param contractingEntityId The ID of the contracting person or entity
     */
    public Project(CreateProjectCommand command, ProjectStatus status, PersonId contractingEntityId) {
        this.projectName = new ProjectName(command.projectName());
        this.description = new Description(command.description());
        this.status = status;
        this.dateRange = new DateRange(command.startDate(), command.endDate());
        this.organizationId = new OrganizationId(command.organizationId());
        this.contractingEntityId = contractingEntityId;
    }

    /**
     * Updates editable fields of the project in a partial and atomic operation.
     *
     * @param projectName     the new name of the project (optional, ignored if null or blank)
     * @param description     the new description of the project (optional, ignored if null or blank)
     * @param newStatus       the new status of the project (optional)
     * @param newEndingDate   the new ending date of the project (optional)
     * @return                the updated project instance
     */
    public Project updateInformation(String projectName, String description, ProjectStatus newStatus, Date newEndingDate) {
        if (!projectName.isBlank()) this.projectName = new ProjectName(projectName);
        if (!description.isBlank()) this.description = new Description(description);
        if (newStatus != null) this.status = newStatus;
        if (newEndingDate != null) this.dateRange = new DateRange(this.dateRange.startDate(), newEndingDate);

        return this;
    }

    /**
     * Updates the project's projectName.
     *
     * @param newName the new projectName of the project
     * @throws IllegalArgumentException if the new projectName is null
     */
    public void updateProjectName(ProjectName newName) {
        if (newName == null) {
            throw new IllegalArgumentException("Project projectName cannot be null");
        }
        this.projectName = newName;
    }

    /**
     * Updates the project's description.
     *
     * @param newDescription the new description of the project
     * @throws IllegalArgumentException if the new description is null
     */
    public void updateDescription(Description newDescription) {
        if (newDescription == null) {
            throw new IllegalArgumentException("Project description cannot be null");
        }
        this.description = newDescription;
    }

    /**
     * Reassigns the current project status.
     *
     * @param newStatus the new {@link ProjectStatus} to be assigned
     * @throws IllegalArgumentException if the new status is null
     */
    public void reassignStatus(ProjectStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Project status cannot be null");
        }
        this.status = newStatus;
    }

    /**
     * Registers a {@link ProjectCreatedEvent} after the project has been successfully created and
     * persisted, signaling that post-creation actions such as initial team member assignment can proceed.
     *
     * <p>This method is intended to be called **after** the entity has been saved, ensuring that
     * the project ID and other relevant data are available for the event payload.</p>
     *
     * <p>The event is stored temporarily and will be dispatched by the application event publisher
     * at the appropriate time in the transaction lifecycle.</p>
     *
     * @see com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.eventhandlers.ProjectCreatedEventHandler
     */
    public void projectCreated() {
        this.registerEvent(new ProjectCreatedEvent(
                this,
                this.getOrganizationId(),
                new ProjectId(this.getId()))
        );
    }

    public void removeTeamMemberById(Long teamMemberId) {
        ProjectTeamMember teamMember = teamMembers.stream()
                .filter(tm -> tm.getId().equals(teamMemberId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No team member found with ID: " + teamMemberId));

        teamMembers.remove(teamMember);
    }
}

