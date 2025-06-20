package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;

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
public class Project extends AuditableAbstractAggregateRoot<Project> {

    /** Name of the project, encapsulated in a description object. */
    @Getter
    @Embedded
    private ProjectName projectName;

    /** Description of the project, encapsulated in a description object. */
    @Getter
    @Embedded
    private Description description;

    /** Range of dates within the project will be done. */
    @Getter
    @Embedded
    private DateRange dateRange;

    /** Identifier of the organization responsible for the project. */
    @Getter
    @Embedded
    private OrganizationId organizationId;

    /** Identifier of the person or entity in charge of contracting. */
    @Getter
    @Embedded
    private PersonId contractingEntityId;

    /** Current status of the project, represented as an entity. */
    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_id", nullable = false, unique = false)
    private ProjectStatus status;

    /** Default constructor required by JPA. */
    public Project() {}

    /**
     * Constructs a project with the required fields.
     *
     * @param projectName the projectName of the project
     * @param description the project's description
     * @param status the current status of the project
     * @param dateRange the range within the project activities will be done
     * @param organizationId the ID of the organization in charge of the project.
     * @param contractingEntityId the ID of the person who
     */
    public Project(ProjectName projectName, Description description, ProjectStatus status, DateRange dateRange, OrganizationId organizationId, PersonId contractingEntityId) {
        this.projectName = projectName;
        this.description = description;
        this.status = status;
        this.dateRange = dateRange;
        this.organizationId = organizationId;
        this.contractingEntityId = contractingEntityId;
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
}

