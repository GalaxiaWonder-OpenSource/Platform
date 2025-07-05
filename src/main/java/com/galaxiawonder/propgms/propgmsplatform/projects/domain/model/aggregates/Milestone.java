package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateMilestoneCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Milestone
 *
 * @summary
 * Aggregate root that represents a project milestone.
 * Uses indirect references to associated milestone items for better modularity and aggregate boundaries.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
public class Milestone extends AuditableAbstractAggregateRoot<Milestone> {

    /**
     * The name of the milestone.
     * A short, descriptive label that identifies the milestone within the project.
     */
    @Getter
    @Setter
    @Embedded
    private MilestoneName name;

    /**
     * A detailed description of the milestone's purpose, scope, or deliverables.
     */
    @Getter
    @Setter
    @Embedded
    private Description description;

    /**
     * The identifier of the project to which this milestone belongs.
     * Provides a contextual link between the milestone and its parent project.
     */
    @Embedded
    @Getter
    private ProjectId projectId;

    @Embedded
    @Getter
    private DateRange dateRange;

    public Milestone() {}

    public Milestone(CreateMilestoneCommand command) {
        this.name = command.name();
        this.description = command.description();
        this.projectId = command.projectId();
        this.dateRange = command.dateRange();
    }

    public void reassignName(MilestoneName name) {
        this.name = name;
    }

    public void reassignDescription(Description description) {
        this.description = description;
    }

    public void reassignDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public void validateDateRange(DateRange projectDateRange) {
        if (this.dateRange.startDate().after(this.dateRange.endDate())) {
            throw new IllegalArgumentException("The start date of the milestone cannot be after the end date of the milestone");
        }

        if (this.dateRange.startDate().before(projectDateRange.startDate())) {
            throw new IllegalArgumentException("The start date of the milestone cannot be before the start date of the project");
        }

        if (this.dateRange.endDate().after(projectDateRange.endDate())) {
            throw new IllegalArgumentException("The end date of the milestone cannot be after the end date of the project");
        }
    }

}

