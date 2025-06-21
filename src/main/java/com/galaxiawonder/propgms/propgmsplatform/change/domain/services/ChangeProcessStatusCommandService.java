package com.galaxiawonder.propgms.propgmsplatform.change.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.SeedChangeProcessStatusCommand;

public interface ChangeProcessStatusCommandService {

    void handle (SeedChangeProcessStatusCommand command);
}
