package com.galaxiawonder.propgms.propgmsplatform.change.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.queries.GetChangeProcessByProjectIdQuery;

import java.util.List;

public interface ChangeProcessQueryService {
    List<ChangeProcess> handle(GetChangeProcessByProjectIdQuery query);
}
