package com.galaxiawonder.propgms.propgmsplatform.change.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.CreateChangeProcessCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.RespondToChangeCommand;

import java.util.Optional;

public interface ChangeProcessCommandService {

    Optional<ChangeProcess> handle(CreateChangeProcessCommand command);

    Optional<ChangeProcess> handle(RespondToChangeCommand command);
}
