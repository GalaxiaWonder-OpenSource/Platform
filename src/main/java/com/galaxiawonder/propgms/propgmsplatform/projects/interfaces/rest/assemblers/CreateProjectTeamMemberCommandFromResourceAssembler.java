package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectTeamMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateProjectTeamMemberResource;

import static com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Specialties.NON_APPLICABLE;

public class CreateProjectTeamMemberCommandFromResourceAssembler {
    public static CreateProjectTeamMemberCommand toCommandFromResource(CreateProjectTeamMemberResource resource) {
        return new CreateProjectTeamMemberCommand(resource.projectId(), resource.orgMemberId(), resource.personId(), NON_APPLICABLE);
    }
}
