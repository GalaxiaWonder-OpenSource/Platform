package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ChangeResponse(String response) {

    public ChangeResponse() {this(null);}

    public ChangeResponse {
        if (response==null || response.isBlank()) {
            throw new IllegalStateException("Response cannot be null or empty.");
        }
        if (response.length()>100) {
            throw new IllegalStateException("Response cannot be longer than 100 characters.");
        }
    }
}
