package com.galaxiawonder.propgms.propgmsplatform.projects.application.acl;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetProjectByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectInfo;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.acl.ProjectContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectContextFacadeImpl implements ProjectContextFacade {
    private final ProjectQueryService projectQueryService;

    public ProjectContextFacadeImpl( ProjectQueryService projectQueryService){
        this.projectQueryService = projectQueryService;
    }

    public Optional<ProjectInfo> getProjectInformationByProjectId(Long projectId){
        return projectQueryService.handle(new GetProjectByProjectIdQuery(projectId));
    }
}
