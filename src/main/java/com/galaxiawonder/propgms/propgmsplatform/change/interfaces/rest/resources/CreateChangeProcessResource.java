package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources;

public record CreateChangeProcessResource(String origin, String status, String justification, Long projectId) {
    public CreateChangeProcessResource {
        if (origin == null || origin.isBlank()) throw new IllegalArgumentException("origin cannot be null or empty");
        if (status == null || status.isBlank()) throw new IllegalArgumentException("status cannot be null or empty");
        if (justification == null || justification.isBlank()) throw new IllegalArgumentException("justification cannot be null or empty");
        if (projectId == null || projectId.equals(0L)) throw new IllegalArgumentException("projectId cannot be null or 0");
    }
}
