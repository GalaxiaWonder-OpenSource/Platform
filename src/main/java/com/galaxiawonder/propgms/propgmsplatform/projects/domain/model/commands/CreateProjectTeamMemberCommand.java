package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

public record CreateProjectTeamMemberCommand(Long organizationMemberId, Long projectId) {
    /**
     * @throws IllegalArgumentException if organizationMemberId is null
     * @throws IllegalArgumentException if projectId is null or zero
     */
    public CreateProjectTeamMemberCommand {
        if (organizationMemberId == null) throw new IllegalArgumentException("Organization member id cannot be null");
        if (projectId == null || projectId.equals(0L)) throw new IllegalArgumentException("Project id cannot be null or zero");
    }

}