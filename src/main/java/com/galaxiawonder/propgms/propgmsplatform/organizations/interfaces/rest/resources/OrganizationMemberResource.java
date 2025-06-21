package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources;

import java.util.Date;

public record OrganizationMemberResource(
        Long id,
        String fullName,
        String memberType,
        Date joinedAt
) {
}
