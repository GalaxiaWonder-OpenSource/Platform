package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import jakarta.annotation.Nullable;

import java.util.Date;

public record TaskResource(long id, String name, String description, Date startDate, Date endDate, long milestoneId, String specialty, String status, @Nullable Long personId) {
}
