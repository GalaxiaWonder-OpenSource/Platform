package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTeamMembersByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectTeamMemberQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectTeamMemberRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectTeamMemberQueryServiceImpl implements ProjectTeamMemberQueryService {
    private final ProjectTeamMemberRepository projectTeamMemberRepository;

    public ProjectTeamMemberQueryServiceImpl(ProjectTeamMemberRepository projectTeamMemberRepository) {
        this.projectTeamMemberRepository = projectTeamMemberRepository;
    }

    public Optional<List<ProjectTeamMember>> handle(GetAllTeamMembersByProjectIdQuery query){
        var projectId = new ProjectId(query.projectId());
        return projectTeamMemberRepository.findAllByProjectId(projectId);
    }
}
