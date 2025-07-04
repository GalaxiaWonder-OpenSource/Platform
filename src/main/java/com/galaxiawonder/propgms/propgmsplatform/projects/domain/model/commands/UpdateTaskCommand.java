package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.TaskStatus;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneItemName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;

public record UpdateTaskCommand(Long id, MilestoneItemName name, Description description, DateRange dateRange, TaskStatus status, PersonId personId, Boolean RemovePerson) {
    /**
     * Compact constructor to enforce validation rules.
     *
     * @throws IllegalArgumentException if id is null or invalid
     */
    public UpdateTaskCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id cannot be null or less than 1");
        }
        // Other fields are optional and may be null
    }
}
