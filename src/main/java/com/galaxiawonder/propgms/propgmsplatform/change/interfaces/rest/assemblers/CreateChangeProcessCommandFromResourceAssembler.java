package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.CreateChangeProcessCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.Justification;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources.CreateChangeProcessResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;

public class CreateChangeProcessCommandFromResourceAssembler {
    public static CreateChangeProcessCommand toCommandFromResource(long Id, CreateChangeProcessResource resource){
        return new CreateChangeProcessCommand(new Justification(resource.justification()), new ProjectId(Id));
    }
}
