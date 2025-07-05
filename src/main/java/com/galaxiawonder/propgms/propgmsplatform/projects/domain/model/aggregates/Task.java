package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateTaskCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.MilestoneItem;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.Specialty;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskSubmission;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.*;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.MilestoneId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Task extends MilestoneItem {
    @Setter
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

    public Task(CreateTaskCommand command){
        this.setName(new MilestoneItemName(command.Name()));
        this.setDescription(new Description(command.Description()));
        this.setRange(new DateRange(command.StartDate(), command.EndDate()));
        this.setMilestoneId(new MilestoneId(command.MilestoneId()));
    }

    public void toDraft(TaskStatus status) {
        this.status = status;
        this.personId = null;
    }

    public void reassignPerson(PersonId personId){
        this.personId = personId;
    }

    public void reassignStatus(TaskStatus status){
        this.status = status;
    }

    public void reassignName(MilestoneItemName name){
        this.setName(name);
    }

    public void reassignDescription(Description description){
        this.setDescription(description);
    }

    public void reassignDateRange(DateRange range){
        this.setRange(range);
    }

    public void validateDateRange(DateRange projectDateRange) {
        if (this.getRange().startDate().after(this.getRange().endDate())) {
            throw new IllegalArgumentException("The start date of the task cannot be after the end date of the milestone");
        }

        if (this.getRange().startDate().before(projectDateRange.startDate())) {
            throw new IllegalArgumentException("The start date of the task cannot be before the start date of the milestone");
        }

        if (this.getRange().endDate().after(projectDateRange.endDate())) {
            throw new IllegalArgumentException("The end date of the task cannot be after the end date of the milestone");
        }
    }
}
