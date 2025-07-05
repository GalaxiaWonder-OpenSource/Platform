package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import jakarta.annotation.Nullable;

import java.util.Date;

public record UpdateMilestoneResource(@Nullable String name, @Nullable String description, @Nullable Date startDate, @Nullable Date endDate) {
}
