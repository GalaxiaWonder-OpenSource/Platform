package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;

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
    private ProjectId projectId;
}

