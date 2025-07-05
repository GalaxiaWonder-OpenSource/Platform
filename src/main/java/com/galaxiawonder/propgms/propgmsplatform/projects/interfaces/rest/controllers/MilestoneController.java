package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.MilestoneCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.CreateMilestoneCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.MilestoneResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateMilestoneResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.MilestoneResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/milestones", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Milestones", description = "Endpoints for Milestones")
public class MilestoneController {
    private final MilestoneCommandService milestoneCommandService;

    public MilestoneController(MilestoneCommandService milestoneCommandService) {
        this.milestoneCommandService = milestoneCommandService;
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
}
