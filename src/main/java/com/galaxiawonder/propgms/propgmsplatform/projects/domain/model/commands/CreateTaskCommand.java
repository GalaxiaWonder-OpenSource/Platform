package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import jakarta.annotation.Nullable;

import java.util.Date;

public record CreateTaskCommand(String Name, String Description, Date StartDate, Date EndDate, Long MilestoneId, String Specialty, @Nullable String Status, @Nullable Long PersonId) {
}
