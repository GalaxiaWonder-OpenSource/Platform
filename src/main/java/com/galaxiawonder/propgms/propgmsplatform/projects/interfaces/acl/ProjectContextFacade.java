package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.acl;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectInfo;

import java.util.Optional;

public interface ProjectContextFacade {

     Optional<ProjectInfo> getProjectInformationByProjectId(Long projectId);
}
