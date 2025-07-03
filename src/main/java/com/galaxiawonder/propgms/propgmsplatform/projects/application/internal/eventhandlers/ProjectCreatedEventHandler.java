package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.eventhandlers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.acl.OrganizationContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectTeamMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.events.ProjectCreatedEvent;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectTeamMemberCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * ProjectCreatedEventHandler
 *
 * @summary
 * Event handler that listens for {@link ProjectCreatedEvent} and automatically
 * assigns the contractor as the first project team member upon project creation.
 *
 * <p>This handler reacts to the domain event emitted when a new project is created.
 * It uses the {@link OrganizationContextFacade} to identify the contractor associated
 * with the organization, and delegates the creation of a {@link com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember}
 * via the {@link ProjectTeamMemberCommandService}.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class ProjectCreatedEventHandler {
    /** Facade that provides access to organizational context such as contractor and member IDs. */
    private final OrganizationContextFacade organizationContextFacade;

    /** Service that handles commands related to project team member management. */
    private final ProjectTeamMemberCommandService projectTeamMemberCommandService;

    public ProjectCreatedEventHandler(
            OrganizationContextFacade organizationContextFacade,
            ProjectTeamMemberCommandService projectTeamMemberCommandService) {
        this.organizationContextFacade = organizationContextFacade;
        this.projectTeamMemberCommandService = projectTeamMemberCommandService;
    }

    /**
     * Handles the {@link ProjectCreatedEvent} and registers the contractor
     * as a project team member with a default specialty of "NON_APPLICABLE".
     *
     * @param event the domain event triggered after a project is created
     */
    @EventListener
    public void on(ProjectCreatedEvent event) {
        Long organizationId = event.getOrganizationId().organizationId();
        Long personId = this.organizationContextFacade.getContractorIdFromOrganizationId(organizationId);
        Long memberId = this.organizationContextFacade.getOrganizationMemberIdFromPersonAndOrganizationId(personId, organizationId);

        this.projectTeamMemberCommandService.handle(
                new CreateProjectTeamMemberCommand(
                        memberId,
                        event.getProjectId().projectId(),
                        "NON_APPLICABLE",
                        personId
                )
        );
    }
}
