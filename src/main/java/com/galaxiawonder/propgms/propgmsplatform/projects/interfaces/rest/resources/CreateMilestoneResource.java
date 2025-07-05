package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.DateRange;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Description;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.MilestoneName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;

import java.util.Date;

public record CreateMilestoneResource(String name, String description, Long projectId, Date startDate, Date endDate) {
}
