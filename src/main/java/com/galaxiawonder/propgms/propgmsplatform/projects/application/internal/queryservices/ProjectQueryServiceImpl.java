package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetProjectByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetProjectInfoByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByTeamMemberPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectInfo;
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
    public Optional<ProjectInfo> handle(GetProjectInfoByProjectIdQuery query){
        var project =  projectRepository.findById(query.projectId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "The project with the id " + query.projectId() + "does not exists"
                ));
        var projectInfo = new ProjectInfo(project.getId(), project.getProjectName().projectName(), project.getStatus().getStringName());
        return Optional.of(projectInfo);
    }

    @Override
    public Optional<Project> handle(GetProjectByProjectIdQuery query){
        return projectRepository.findById(query.projectId());
    }
}
