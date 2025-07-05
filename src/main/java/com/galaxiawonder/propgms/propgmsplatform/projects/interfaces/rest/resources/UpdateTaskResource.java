package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import jakarta.annotation.Nullable;

import java.util.Date;

public record UpdateTaskResource(
        @Nullable String name,
        @Nullable String description,
        @Nullable Date startDate,
        @Nullable Date endDate,
        @Nullable String status,
        @Nullable Long personId,
        @Nullable Boolean removePerson
) {
    public UpdateTaskResource {
        if (removePerson == null) {
            removePerson = false;
        }
    }
}

