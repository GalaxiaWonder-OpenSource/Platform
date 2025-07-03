package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands;

public record CreateProjectTeamMemberCommand(Long organizationMemberId, Long projectId, String specialty, Long personId){
    /**
     * @throws IllegalArgumentException if personId is null or zero
     * @throws IllegalArgumentException if projectId is null or zero
     * @throws IllegalArgumentException if organizationMemberId is null
     */
       public CreateProjectTeamMemberCommand{
           if (organizationMemberId == null) throw new IllegalArgumentException("Organization member id cannot be null or zero");
           if (personId == null) throw new IllegalArgumentException("Person id cannot be null or zero");
           if (projectId == null || projectId.equals(0L)) throw new IllegalArgumentException("Project id cannot be null or zero");
           if (specialty == null) throw new IllegalArgumentException("Speciality must be choose");
       }

}
