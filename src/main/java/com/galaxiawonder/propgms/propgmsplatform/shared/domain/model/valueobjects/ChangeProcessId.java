package com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ChangeProcessId(Long changeProcessId) {
    public ChangeProcessId {
        if (changeProcessId == null || changeProcessId < 1 ) {
            throw new IllegalArgumentException("changeProcessId must be a positive integer");
        }
    }
}
