package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.queries.GetChangeProcessByProjectIdQuery;

public class GetChangeProcessByProjectIdQueryAssembler {
    public static GetChangeProcessByProjectIdQuery toQueryFromResource(long projectId){
        return new GetChangeProcessByProjectIdQuery(projectId);
    }
}
