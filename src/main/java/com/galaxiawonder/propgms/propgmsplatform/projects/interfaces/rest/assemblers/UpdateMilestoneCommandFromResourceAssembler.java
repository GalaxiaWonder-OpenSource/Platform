package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateMilestoneCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneName;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.UpdateMilestoneResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;

public class UpdateMilestoneCommandFromResourceAssembler {
    public static UpdateMilestoneCommand toCommandFromResource(long milestoneId, UpdateMilestoneResource resource) {

        MilestoneName name = null;
        if (resource.name() != null && !resource.name().isBlank()) {
            name = new MilestoneName(resource.name());
        }

        Description description = null;
        if (resource.description() != null && !resource.description().isBlank()) {
            description = new Description(resource.description());
        }

        DateRange dateRange = null;
        if (resource.startDate() != null && resource.endDate() != null) {
            dateRange = new DateRange(resource.startDate(), resource.endDate());
        }

        return new UpdateMilestoneCommand(
                milestoneId,
                name,
                description,
                dateRange
        );
    }
}

