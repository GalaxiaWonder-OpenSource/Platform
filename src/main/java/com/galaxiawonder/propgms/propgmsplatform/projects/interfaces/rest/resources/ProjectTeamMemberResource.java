package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources;

import java.util.Date;

public record ProjectTeamMemberResource(
        Long id,
        String name,
        String email,
        String teamMemberType,
        String speciality) {
}
