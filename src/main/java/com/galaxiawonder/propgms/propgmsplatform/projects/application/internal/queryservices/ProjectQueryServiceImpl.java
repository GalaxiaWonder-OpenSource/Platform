package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByTeamMemberPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTeamMembersByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectQueryServiceImpl implements ProjectQueryService {
    private final ProjectRepository projectRepository;

    public ProjectQueryServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> handle(GetAllProjectsByTeamMemberPersonIdQuery query) {
        return projectRepository.findAllProjectsByTeamMemberPersonId(query.personId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "The person with the ID " + query.personId() + " is not part of any project team"));
    }
    @Override
    public List<ProjectTeamMember> handle(GetAllTeamMembersByProjectIdQuery query) {
        return projectRepository.findAllTeamMembersByProjectId(query.projectId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "The project with the ID " + query.projectId() + " has no team members"));
    }

}
