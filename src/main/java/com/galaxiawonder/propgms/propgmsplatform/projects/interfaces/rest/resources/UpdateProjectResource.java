package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectStatuses;
import jakarta.annotation.Nullable;

import java.util.Date;

public record UpdateProjectResource(
    @Nullable String name,
    @Nullable String description,
    @Nullable
    ProjectStatuses status,
    Date endingDate
){
}