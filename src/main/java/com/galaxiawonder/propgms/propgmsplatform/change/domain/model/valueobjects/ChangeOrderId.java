package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ChangeOrderId(Long changeOrderId) {
    /**
     *
     */
    public ChangeOrderId {
        if ( changeOrderId == null || changeOrderId < 1 ) {
            throw new IllegalArgumentException("changeOrderId must be a positive integer");
        }
    }
}
