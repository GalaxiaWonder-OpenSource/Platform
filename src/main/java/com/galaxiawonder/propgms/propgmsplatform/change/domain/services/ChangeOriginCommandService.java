package com.galaxiawonder.propgms.propgmsplatform.change.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.SeedChangeOriginCommand;

public interface ChangeOriginCommandService {

    void handle (SeedChangeOriginCommand command);
}
