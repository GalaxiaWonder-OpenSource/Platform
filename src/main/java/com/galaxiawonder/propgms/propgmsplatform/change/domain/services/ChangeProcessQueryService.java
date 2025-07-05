package com.galaxiawonder.propgms.propgmsplatform.change.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.queries.GetChangeProcessesByProjectIdQuery;

import java.util.List;

public interface ChangeProcessQueryService {
    List<ChangeProcess> handle(GetChangeProcessesByProjectIdQuery query);
}
