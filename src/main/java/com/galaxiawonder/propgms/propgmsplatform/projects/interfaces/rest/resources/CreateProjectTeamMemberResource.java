package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

public record CreateProjectTeamMemberResource(
        Long orgMemberId,
        Long projectId,
        Long personId,
        String Specialty

) {
}
