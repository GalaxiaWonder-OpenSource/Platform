package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneItemName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.entities.AuditableModel;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.MilestoneId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * MilestoneItem
 *
 * @summary
 * Abstract base class for items that belong to a milestone,
 * such as tasks or meetings.
 *
 * Uses the {@link InheritanceType#JOINED} strategy to map each concrete subclass
 * to its own dedicated database table, while preserving common fields in a shared base.
 *
 * Common fields include projectName, description, startDate and endDate dates, and audit metadata.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MilestoneItem extends AuditableAbstractAggregateRoot<MilestoneItem> {

    /**
     * The projectName of the milestone item.
     * Typically a short, human-readable label like a task title or meeting topic.
     */
    @Getter
    @Setter
    @Embedded
    private MilestoneItemName name;

    /**
     * A longer description providing additional context or objectives for the item.
     */
    @Getter
    @Setter
    @Embedded
    private Description description;

    @Getter
    @Setter
    @Embedded
    private DateRange range;

    /**
     * The identifier of the milestone this item belongs to.
     * Establishes the association between this item and its parent milestone.
     */
    @Getter
    @Setter
    @Embedded
    private MilestoneId milestoneId;
}
