package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.commands.RespondToChangeCommand;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.entities.ChangeProcessStatus;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeProcessStatuses;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.valueobjects.ChangeResponse;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources.RespondToChangeProcessResource;

public class RespondToChangeProcessCommandFromResourceAssembler {
    public static RespondToChangeCommand toCommandFromResource(Long changeProcessId, RespondToChangeProcessResource resource){
        return new RespondToChangeCommand(
                changeProcessId,
                new ChangeResponse(resource.response()),
                new ChangeProcessStatus(ChangeProcessStatuses.valueOf(resource.status())).getName()
        );
    }
}
