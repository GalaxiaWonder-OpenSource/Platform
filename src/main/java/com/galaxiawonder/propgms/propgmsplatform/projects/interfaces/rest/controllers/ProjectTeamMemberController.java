package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectTeamMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.DeleteProjectTeamMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectTeamMemberCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.CreateProjectTeamMemberCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.ProjectTeamMemberResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateProjectTeamMemberResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.ProjectTeamMemberResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.interfaces.rest.resources.GenericMessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/project-team-members")
@Tag(name = "Project Team Members", description = "Endpoints for Project Team Members")
public class ProjectTeamMemberController {
    private final ProjectTeamMemberCommandService projectTeamMemberCommandService;

    public ProjectTeamMemberController(ProjectTeamMemberCommandService projectTeamMemberCommandService) {
        this.projectTeamMemberCommandService = projectTeamMemberCommandService;
    }

    @Operation(
            summary = "Create a project team member",
            description = "Creates a project team member based on the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project team member created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<ProjectTeamMemberResource> createProjectTeamMember(CreateProjectTeamMemberResource resource) {
        Optional<ProjectTeamMember> projectTeamMember = projectTeamMemberCommandService
                .handle(CreateProjectTeamMemberCommandFromResourceAssembler.toCommandFromResource(resource));
        return projectTeamMember
                .map(source -> new ResponseEntity<>(ProjectTeamMemberResourceFromEntityAssembler.toResourceFromEntity(source), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Operation(
            summary = "Delete a project team member by Id",
            description = "Deletes a project team member with the given Id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project team member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project team member not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<GenericMessageResource> deleteProjectTeamMember(@PathVariable Long id){
        var deleteProjectTeamMember = new DeleteProjectTeamMemberCommand(id);
        projectTeamMemberCommandService.handle(deleteProjectTeamMember);
        return ResponseEntity.ok(new GenericMessageResource("Project team member successfully deleted"));
    }
}
