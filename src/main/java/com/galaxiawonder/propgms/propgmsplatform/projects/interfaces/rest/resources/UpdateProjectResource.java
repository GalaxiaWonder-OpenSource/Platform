package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import jakarta.annotation.Nullable;

import java.util.Date;

public record UpdateProjectResource(
    @Nullable String name,
    @Nullable String description,
    @Nullable String status,
    @Nullable Date endingDate
){
}