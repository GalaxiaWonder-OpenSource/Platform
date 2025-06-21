package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;

public record RespondToChangeCommand(ChangeProcessStatuses status, Long responsedBy, String notes) {
    public RespondToChangeCommand {
        if (status == null) {
            throw new IllegalStateException("Approved status cannot be null.");
        }
        if (responsedBy == null || responsedBy < 1) {
            throw new IllegalStateException("ResponsedBy cannot be null or less than zero");
        }
        if (notes == null || notes.isEmpty()) {
            throw new IllegalStateException("Notes cannot be null or empty.");
        }
    }
}
