package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

public record CreateProjectTeamMemberCommand(
        Long projectId,
        Long personId,
        Long organizationMemberId,
        String specialty
) {
}
