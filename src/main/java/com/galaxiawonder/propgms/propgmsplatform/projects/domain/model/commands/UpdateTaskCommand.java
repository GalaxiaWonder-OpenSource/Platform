package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

import jakarta.annotation.Nullable;

import java.util.Date;

public record UpdateTaskCommand(Long id, @Nullable String name, @Nullable String description, @Nullable Date startDate, @Nullable Date endDate, @Nullable String status, @Nullable Long personId, boolean removePerson) {
}
