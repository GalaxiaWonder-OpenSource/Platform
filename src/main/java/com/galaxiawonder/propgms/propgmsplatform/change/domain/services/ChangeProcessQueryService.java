package com.galaxiawonder.propgms.propgmsplatform.change.domain.services;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.queries.GetChangeProcessByProjectIdQuery;

import java.util.List;
import java.util.Optional;

public interface ChangeProcessQueryService {
    Optional<ChangeProcess> handle(GetChangeProcessByProjectIdQuery query);
}
