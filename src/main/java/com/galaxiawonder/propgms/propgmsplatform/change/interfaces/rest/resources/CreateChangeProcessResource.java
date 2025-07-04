package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources;

public record CreateChangeProcessResource(String justification, Long projectId) {
    public CreateChangeProcessResource {
        if (justification == null || justification.isBlank()) throw new IllegalArgumentException("justification cannot be null or empty");
        if (projectId == null || projectId.equals(0L)) throw new IllegalArgumentException("projectId cannot be null or 0");
    }
}
