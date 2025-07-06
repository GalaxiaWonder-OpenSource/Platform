package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.ProjectTeamMemberResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProfileDetails;

/**
 * Converts a pair of {@link ProjectTeamMember} and {@link ProfileDetails} into
 * an {@link ProjectTeamMemberResource}.
 *
 * @return a resource DTO representing the organization member
 */
public class ProjectTeamMemberResourceFromEntityAssembler {
    public static ProjectTeamMemberResource toResourceFromEntity(ProjectTeamMember entity) {
        return new ProjectTeamMemberResource(
                entity.getId(),
                entity.getName().getFullName(),
                entity.getEmail().address(),
                entity.getMemberType().getName().name(),
                entity.getSpecialty().getName().name()
        );
    }
}
