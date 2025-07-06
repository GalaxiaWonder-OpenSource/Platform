package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import jakarta.annotation.Nullable;

import java.util.Date;

public record CreateTaskResource(String name, String description, Date startDate, Date endDate, Long milestoneId, String specialty, @Nullable String status, @Nullable Long personId) {
}
