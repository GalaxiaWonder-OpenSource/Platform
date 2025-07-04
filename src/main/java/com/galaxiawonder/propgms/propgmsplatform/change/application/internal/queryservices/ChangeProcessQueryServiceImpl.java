package com.galaxiawonder.propgms.propgmsplatform.change.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.queries.GetChangeProcessByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.services.ChangeProcessQueryService;
import com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories.ChangeProcessRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChangeProcessQueryServiceImpl implements ChangeProcessQueryService {

    private final ChangeProcessRepository changeProcessRepository;

    public ChangeProcessQueryServiceImpl(ChangeProcessRepository changeProcessRepository) {
        this.changeProcessRepository = changeProcessRepository;
    }

    @Override
    public Optional<ChangeProcess> handle(GetChangeProcessByProjectIdQuery query){
        return changeProcessRepository.findByProjectId(new ProjectId(query.projectId()));
    }
}
