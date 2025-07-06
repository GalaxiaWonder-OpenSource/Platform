package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.DeleteMilestoneCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllMilestonesByProjectIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.MilestoneCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.MilestoneQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.CreateMilestoneCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.MilestoneResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.UpdateMilestoneCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateMilestoneResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.MilestoneResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.UpdateMilestoneResource;
import com.galaxiawonder.propgms.propgmsplatform.shared.interfaces.rest.resources.GenericMessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/milestones", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Milestones", description = "Endpoints for Milestones")
public class MilestoneController {
    private final MilestoneCommandService milestoneCommandService;
    private final MilestoneQueryService milestoneQueryService;

    public MilestoneController(MilestoneCommandService milestoneCommandService, MilestoneQueryService milestoneQueryService) {
        this.milestoneCommandService = milestoneCommandService;
        this.milestoneQueryService = milestoneQueryService;
    }

    @Operation(
            summary = "Create a Milestone",
            description = "Creates a milestone based on the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Milestone created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
    })
    @PostMapping
    public ResponseEntity<MilestoneResource> createMilestone(@RequestBody CreateMilestoneResource resource){
        Optional<Milestone> milestone = milestoneCommandService
                .handle(CreateMilestoneCommandFromResourceAssembler.toCommandFromResource(resource));
        return milestone
                .map(source -> new ResponseEntity<>(MilestoneResourceFromEntityAssembler.toResourceFromEntity(source), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Operation(
            summary = "Get all milestones by project ID",
            description = "Retrieves all milestones associated with a specific project"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Milestones retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No milestones found for the project")
    })
    @GetMapping("/by-project-id/{projectId}")
    public ResponseEntity<List<MilestoneResource>> getAllMilestonesByProjectId(@PathVariable Long projectId) {
        Optional<List<Milestone>> milestones = milestoneQueryService
                .handle(new GetAllMilestonesByProjectIdQuery(projectId));

        List<MilestoneResource> resources = milestones.map(source -> source.stream()
                        .map(MilestoneResourceFromEntityAssembler::toResourceFromEntity)
                        .toList())
                .orElseGet(List::of);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(
            summary = "Update milestone by ID",
            description = "Updates a milestone by a milestone ID with new information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Milestone updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
    })
    @PatchMapping("{milestoneId}")
    public ResponseEntity<MilestoneResource> updateMilestone(@PathVariable Long milestoneId,
                                                             UpdateMilestoneResource resource){
        Optional<Milestone> milestoneUpdated = milestoneCommandService
                .handle(UpdateMilestoneCommandFromResourceAssembler.toCommandFromResource(milestoneId, resource));
        return milestoneUpdated
                .map(source -> new ResponseEntity<>(MilestoneResourceFromEntityAssembler.toResourceFromEntity(source), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Operation(
            summary = "Delete milestone by Id",
            description = "Deletes a milestone with the given Id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Milestone deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Milestone not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<GenericMessageResource> deleteMilestone(@PathVariable Long id){
        var deleteMilestoneCommand = new DeleteMilestoneCommand(id);
        milestoneCommandService.handle(deleteMilestoneCommand);
        return ResponseEntity.ok(new GenericMessageResource("Milestone successfully deleted"));
    }
}
