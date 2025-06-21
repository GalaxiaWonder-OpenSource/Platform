package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ChangeResponseId(Long changeResponseId) {

    public ChangeResponseId {
        if ( changeResponseId == null || changeResponseId < 1 ) {
            throw new IllegalArgumentException("changeResponseId must be a positive integer");
        }
    }
}
