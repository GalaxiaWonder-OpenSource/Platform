package com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeResponse;

public record RespondToChangeCommand(long id, ChangeResponse response, ChangeProcessStatuses status) {
    public RespondToChangeCommand {
        if (status == null) {
            throw new IllegalStateException("Approved status cannot be null.");
        }
        if (response == null) {
            throw new IllegalStateException("Response cannot be null.");
        }
        if (id < 1) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
    }
}
