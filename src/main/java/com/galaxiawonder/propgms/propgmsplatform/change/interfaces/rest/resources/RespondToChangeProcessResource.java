package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources;

public record RespondToChangeProcessResource(String response, String status) {
    public RespondToChangeProcessResource {
        if (response == null || response.isBlank()) throw new IllegalArgumentException("response cannot be null or empty");
        if (status == null || status.isBlank()) throw new IllegalArgumentException("status cannot be null or empty");
    }
}
