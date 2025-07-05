package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import java.util.Date;

public record MilestoneResource(Long id, String name, String description, Long projectId, Date startDate, Date endDate) {
}
