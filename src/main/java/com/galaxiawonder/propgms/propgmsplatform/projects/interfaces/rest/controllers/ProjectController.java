package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.OrganizationMemberResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.DeleteProjectTeamMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTeamMembersByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.CreateProjectTeamMemberCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.ProjectTeamMemberResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.*;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.DeleteProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByTeamMemberPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.CreateProjectCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.ProjectResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateProjectResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.ProjectResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * AuthenticationController
 *
 * @summary
 * REST controller that manages user authentication-related operations, such as user registration.
 * Exposes endpoints under {@code /api/v1/auth} and returns JSON responses.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/projects", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Projects", description = "Endpoints for Projects")
public class ProjectController {
    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    /**
     * Constructor for ProjectController.
     * @param projectCommandService Project command service
     * @param projectQueryService Project query service}
     * @see ProjectCommandService
     * @see ProjectQueryService
     */

    ProjectController(ProjectCommandService projectCommandService,
                      ProjectQueryService projectQueryService) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }

    /**
     * Creates a Project
     * @param resource CreateProjectResource containing the required params
     * @return ResponseEntity with the created project resource, or bad request if the resource is invalid
     * @see CreateProjectResource
     * @see ProjectResource
     */
    @PostMapping
    public ResponseEntity<ProjectResource> createProject(@RequestBody CreateProjectResource resource) {
        Optional<Project> project = projectCommandService
                .handle(CreateProjectCommandFromResourceAssembler.toCommandFromResource(resource));

        return project
                .map(source -> new ResponseEntity<>(ProjectResourceFromEntityAssembler.toResourceFromEntity(source), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Retrieves all projects where a given person is a team member.
     *
     * @param personId the ID of the person
     * @return a list of {@link ProjectResource} objects representing the projects
     */
    @Operation(
            summary = "Get projects by person ID",
            description = "Retrieves all projects where the given person is registered as a project team member"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found or is not assigned to any projects")
    })
    @GetMapping("/by-person-id/{id}")
    public ResponseEntity<List<ProjectResource>> getAllProjectsByTeamMemberPersonId(
            @Parameter(description = "ID of the person", required = true)
            @PathVariable("id") Long personId
    ) {
        List<Project> projects = projectQueryService.handle(
                new GetAllProjectsByTeamMemberPersonIdQuery(personId)
        );

        List<ProjectResource> resources = projects.stream()
                .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete project by ID",
            description = "Deletes a project with the given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        var deleteProjectCommand = new DeleteProjectCommand(id);
        projectCommandService.handle(deleteProjectCommand);
        return ResponseEntity.ok("Project successfully deleted");
    }
    @PatchMapping("{id}")
    @Operation(
            summary = "Update project by ID",
            description = "Updates a project with the given ID using provided fields"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<?> updateProject(
            @PathVariable Long id,
            @RequestBody UpdateProjectResource resource
    ) {
        var command = new UpdateProjectCommand(
                id,
                resource.name(),
                resource.description(),
                resource.status(),
                resource.endingDate()
        );
        projectCommandService.handle(command);
        return ResponseEntity.ok("Project with given ID successfully updated");
    }

    /**
     * Adds a new team member to a project.
     *
     * @param resource AddProjectTeamMemberResource containing orgMemberId and projectId
     * @return ResponseEntity with the created team member resource
     */
    @Operation(
            summary = "Add team member to project",
            description = "Adds a new team member to the specified project"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Team member added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or member already exists in project"),
            @ApiResponse(responseCode = "404", description = "Project or organization member not found")
    })
    @PostMapping("/{projectId}/team-members")
    public ResponseEntity<ProjectTeamMemberResource> createTeamMemberToProject(
            @RequestBody CreateProjectTeamMemberResource resource
    ) {
        Optional<ProjectTeamMember> teamMember = projectCommandService
                .handle(CreateProjectTeamMemberCommandFromResourceAssembler.toCommandFromResource(resource));

        return teamMember
                .map(source -> new ResponseEntity<>(
                        ProjectTeamMemberResourceFromEntityAssembler.toResourceFromEntity(source),
                        HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Retrieves all team members associated with a specific project.
     *
     * @param projectId the ID of the project
     * @return a list of {@link OrganizationMemberResource} objects
     */
    @Operation(
            summary = "Get all members by project ID",
            description = "Retrieves all active members associated with the given project ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Members retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })

    @GetMapping("/{projectId}/team-members")
    public ResponseEntity<List<ProjectTeamMemberResource>> getTeamMembersByProjectId(
            @Parameter(description = "ID of the project", required = true)
            @PathVariable("projectId") Long projectId
    ) {
        List<ProjectTeamMember> teamMembers = projectQueryService.handle(
                new GetAllTeamMembersByProjectIdQuery(projectId)
        );

        List<ProjectTeamMemberResource> resources = teamMembers.stream()
                .map(ProjectTeamMemberResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    /**
     * Removes a team member from a project.
     *
     * @param teamMemberId the ID of the team member to remove
     * @return ResponseEntity with success message
     */
    @Operation(
            summary = "Remove team member by ID",
            description = "Removes a team member from their assigned project"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team member removed successfully"),
            @ApiResponse(responseCode = "404", description = "Team member not found")
    })
    @DeleteMapping("/team-members/{teamMemberId}")
    public ResponseEntity<String> deleteTeamMemberById(
            @Parameter(description = "ID of the team member to remove", required = true)
            @PathVariable Long teamMemberId
    ) {
        projectCommandService.handle(new DeleteProjectTeamMemberCommand(teamMemberId));
        return ResponseEntity.noContent().build();
    }
}
