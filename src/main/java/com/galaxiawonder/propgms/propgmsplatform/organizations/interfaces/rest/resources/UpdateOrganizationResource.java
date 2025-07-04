package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources;

import jakarta.annotation.Nullable;

public record UpdateOrganizationResource(
        @Nullable String commercialName,
        @Nullable String legalName
) {}

