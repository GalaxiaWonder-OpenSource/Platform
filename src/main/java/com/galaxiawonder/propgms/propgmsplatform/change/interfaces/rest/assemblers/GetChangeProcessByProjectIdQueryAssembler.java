package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.queries.GetChangeProcessesByProjectIdQuery;

public class GetChangeProcessByProjectIdQueryAssembler {
    public static GetChangeProcessesByProjectIdQuery toQueryFromResource(long projectId){
        return new GetChangeProcessesByProjectIdQuery(projectId);
    }
}
