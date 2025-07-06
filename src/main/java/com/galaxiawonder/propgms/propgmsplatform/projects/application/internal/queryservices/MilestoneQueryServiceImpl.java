package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllMilestonesByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.MilestoneQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.MilestoneRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilestoneQueryServiceImpl implements MilestoneQueryService {
    private final MilestoneRepository milestoneRepository;

    public MilestoneQueryServiceImpl(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public Optional<List<Milestone>> handle(GetAllMilestonesByProjectIdQuery query){
        var queryProjectId = new ProjectId(query.projectId());
        return milestoneRepository.findAllByProjectId(queryProjectId);
    }
}
