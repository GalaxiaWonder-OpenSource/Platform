package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import java.util.Date;

public record TaskResource(Long id, String name, String description, Date startDate, Date endDate, Long milestoneId, String specialty, String status, Long personId) {
}
