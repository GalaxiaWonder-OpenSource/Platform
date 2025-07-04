package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources.ChangeProcessResource;
import jakarta.annotation.Nullable;

public class ChangeProcessResourceFromEntityAssembler {
    public static ChangeProcessResource toResourceFromEntity(ChangeProcess entity){
        return new ChangeProcessResource(
                entity.getOrigin().getStringName(),
                entity.getStatus().getStringName(),
                entity.getJustification().justification(),
                entity.getResponse() != null ? entity.getResponse().toString() : null,
                entity.getProjectId().projectId()
        );
    }
}
