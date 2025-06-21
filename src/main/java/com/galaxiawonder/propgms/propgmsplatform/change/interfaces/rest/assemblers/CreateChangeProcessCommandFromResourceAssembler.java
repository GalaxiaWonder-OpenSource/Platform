package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.CreateChangeProcessCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources.CreateChangeProcessResource;

public class CreateChangeProcessCommandFromResourceAssembler {
    public static CreateChangeProcessCommand toCommandFromResource(CreateChangeProcessResource resource){
        return new CreateChangeProcessCommand(resource.origin(), resource.status(), resource.justification(), resource.projectId());
    }
}
