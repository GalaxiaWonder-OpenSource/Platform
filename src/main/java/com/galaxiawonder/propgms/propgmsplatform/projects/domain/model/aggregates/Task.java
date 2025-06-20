package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MilestoneItem;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.Specialty;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskSubmission;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Task extends MilestoneItem {
    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "specialty_id", nullable = false, unique = false)
    private Specialty specialty;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_id", nullable = false, unique = false)
    private TaskStatus status;

    @Getter
    @Embedded
    private PersonId personId;

    /** Task submission associated with this task. */
    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "task_submission_id", unique = true)
    private TaskSubmission taskSubmission;

    public Task() {}
}
