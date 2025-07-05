package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationQueryService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.CreateOrganizationResource;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.rest.resources.OrganizationResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.DeleteProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByContractingEntityIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllProjectsByTeamMemberPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetProjectByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.CreateProjectCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.ProjectResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateProjectResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.ProjectResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.UpdateProjectResource;
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

    @Operation(
            summary = "Get project by ID",
            description = "Retrieves a project by a id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResource> getProjectById(@PathVariable Long projectId) {
        var query = new GetProjectByProjectIdQuery(projectId);
        Optional<Project> project = projectQueryService.handle(query);
        return project.map(source -> new ResponseEntity<>(ProjectResourceFromEntityAssembler.toResourceFromEntity(source), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get project by contracting entity Id",
            description = "Retrieves a project by a contracting entity Id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Projects not found")
    })
    @GetMapping("by-contracting-entity-id/{contractingEntityId}")
    public ResponseEntity<List<ProjectResource>> getProjectsByContractingEntityId(@PathVariable Long contractingEntityId) {
        var query = new GetAllProjectsByContractingEntityIdQuery(contractingEntityId);
        Optional<List<Project>> projects = projectQueryService.handle(query);

        List<ProjectResource> resources = projects.map(source -> source.stream()
                .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                .toList())
                .orElseGet(List::of);

        return new ResponseEntity<>(resources, HttpStatus.OK);
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
    public ResponseEntity<List<ProjectResource>> getProjectsByPersonId(
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
}
