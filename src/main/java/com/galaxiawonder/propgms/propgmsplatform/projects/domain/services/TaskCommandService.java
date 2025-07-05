package com.galaxiawonder.propgms.propgmsplatform.projects.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Task;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateTaskCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateTaskCommand;

import java.util.Optional;

public interface TaskCommandService {

    Optional<Task> handle(CreateTaskCommand command);

    Optional<Task> handle(UpdateTaskCommand command);
}
