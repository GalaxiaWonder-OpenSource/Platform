package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectTeamMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateProjectTeamMemberResource;

public class CreateProjectTeamMemberCommandFromResourceAssembler {
    public static CreateProjectTeamMemberCommand toCommandFromResource(CreateProjectTeamMemberResource resource) {
        return new CreateProjectTeamMemberCommand(resource.projectId(), resource.orgMemberId());
    }
}
