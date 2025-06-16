package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MilestoneItem;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.Specialty;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskStatus;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.TaskSubmissionId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Setter
    @Embedded
    private TaskSubmissionId taskSubmissionId;

    public Task() {}
}
