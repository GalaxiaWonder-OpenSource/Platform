package com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import com.galaxiawonder.propgms.propgmsplatform.change.domain.services.ChangeProcessCommandService;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers.ChangeProcessResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers.CreateChangeProcessCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.assemblers.RespondToChangeProcessCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources.ChangeProcessResource;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources.CreateChangeProcessResource;
import com.galaxiawonder.propgms.propgmsplatform.change.interfaces.rest.resources.RespondToChangeProcessResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value="/api/v1/", produces = APPLICATION_JSON_VALUE)
@Tag(name="Change Process", description = "Endpoints for Change Process")
public class ChangeProcessController {
    private final ChangeProcessCommandService changeProcessCommandService;

    public ChangeProcessController(ChangeProcessCommandService changeProcessCommandService) {
        this.changeProcessCommandService = changeProcessCommandService;
    }

    @PostMapping("projects/{projectId}/change-process")
    public ResponseEntity<ChangeProcessResource>
    createChangeProcess(@PathVariable Long projectId , @RequestBody CreateChangeProcessResource resource) {
        Optional<ChangeProcess> changeProcess = changeProcessCommandService
                .handle(CreateChangeProcessCommandFromResourceAssembler.toCommandFromResource(projectId, resource));
        return changeProcess.map(source -> new ResponseEntity<>(ChangeProcessResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping("change-process/{changeProcessId}")
    public ResponseEntity<ChangeProcessResource>
    respondToChangeProcess(@PathVariable long changeProcessId, @RequestBody RespondToChangeProcessResource resource) {
        Optional<ChangeProcess> changeProcess = changeProcessCommandService
                .handle(RespondToChangeProcessCommandFromResourceAssembler.toCommandFromResource(changeProcessId, resource));
        return changeProcess.map(source -> new ResponseEntity<>(ChangeProcessResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
