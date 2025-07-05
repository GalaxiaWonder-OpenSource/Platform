package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.MilestoneResource;

public class MilestoneResourceFromEntityAssembler {
    public static MilestoneResource toResourceFromEntity(Milestone milestone) {
        return new MilestoneResource(
                milestone.getId(),
                milestone.getName().milestoneName(),
                milestone.getDescription().description(),
                milestone.getProjectId().projectId(),
                milestone.getDateRange().startDate(),
                milestone.getDateRange().endDate()
        );
    }
}
