package com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.acl;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationCommandService;

public interface OrganizationContextFacade {

    Long getContractorIdFromOrganizationId(Long organizationId);

    Long getOrganizationMemberIdFromPersonAndOrganizationId(Long personId, Long organizationId);
}
