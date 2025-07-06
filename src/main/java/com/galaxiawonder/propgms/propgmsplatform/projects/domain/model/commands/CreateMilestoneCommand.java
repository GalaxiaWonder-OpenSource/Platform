package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;

public record CreateMilestoneCommand(MilestoneName name, Description description, ProjectId projectId, DateRange dateRange) {
    public CreateMilestoneCommand {
        if(name == null || name.milestoneName().isBlank()) throw new IllegalArgumentException("Milestone name cannot be null or blank");
        if(description == null || description.description().isBlank()) throw new IllegalArgumentException("Milestone description cannot be null or blank");
        if(projectId == null) throw new IllegalArgumentException("Project id cannot be null");
        if(dateRange == null) throw new IllegalArgumentException("Date range cannot be null");
    }
}
