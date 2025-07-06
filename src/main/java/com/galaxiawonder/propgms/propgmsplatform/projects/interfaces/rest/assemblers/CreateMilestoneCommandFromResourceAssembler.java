package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateMilestoneCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneName;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateMilestoneResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;

public class CreateMilestoneCommandFromResourceAssembler {
    public static CreateMilestoneCommand toCommandFromResource(CreateMilestoneResource resource){
        return new CreateMilestoneCommand(
                new MilestoneName(resource.name()),
                new Description(resource.description()),
                new ProjectId(resource.projectId()),
                new DateRange(resource.startDate(), resource.endDate())
        );
    }
}
