package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.ProjectResource;

public class ProjectResourceFromEntityAssembler {

    /**
     * Converts a {@link Project} domain entity into a {@link ProjectResource} DTO.
     *
     * @param project the project entity to convert
     * @return the corresponding {@link ProjectResource}
     */
    public static ProjectResource toResourceFromEntity(Project project) {
        return new ProjectResource(
                project.getId(),
                project.getProjectName().projectName(),
                project.getDescription().description(),
                project.getStatus().getName().toString(),
                project.getDateRange().startDate(),
                project.getDateRange().endDate(),
                project.getOrganizationId().organizationId(),
                project.getContractingEntityId().personId()
        );
    }
}
