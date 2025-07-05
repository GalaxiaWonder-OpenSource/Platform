package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources;

import jakarta.annotation.Nullable;

public record ChangeProcessResource(Long id, String origin, String status, String justification, @Nullable String response, Long projectId) {
}
