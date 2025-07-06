package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

public record CreateProjectTeamMemberResource(Long organizationMemberId, Long projectId, String specialty, String memberType
) {
}
