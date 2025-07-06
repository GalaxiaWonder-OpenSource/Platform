package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Task;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.DeleteTaskCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTasksByMilestoneIdAndPersonIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.queries.GetAllTasksByMilestoneIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.TaskCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.TaskQueryService;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.CreateTaskCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.TaskResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.UpdateTaskCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateTaskResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.TaskResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.UpdateTaskResource;
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
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Tasks", description = "Endpoints for Tasks")
public class TaskController {
    private final TaskCommandService taskCommandService;
    private final TaskQueryService taskQueryService;

    public TaskController(TaskCommandService taskCommandService,
                          TaskQueryService taskQueryService) {
        this.taskCommandService = taskCommandService;
        this.taskQueryService = taskQueryService;
    }

    @Operation(
            summary = "Create a task",
            description = "Creates a task based on the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<TaskResource> createTask(@RequestBody CreateTaskResource resource){
        Optional<Task> task = taskCommandService
                .handle(CreateTaskCommandFromResourceAssembler.toCommandFromResource(resource));
        return task
                .map(source -> new ResponseEntity<>(TaskResourceFromEntityAssembler.toResourceFromEntity(source), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Operation(
            summary = "Update a task with a given Id",
            description = "Update a task based on the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PatchMapping("{taskId}")
    public ResponseEntity<TaskResource> updateTask(@PathVariable Long taskId, @RequestBody UpdateTaskResource resource){
        Optional<Task> task = taskCommandService
                .handle(UpdateTaskCommandFromResourceAssembler.toCommandFromResource(taskId, resource));
        return task
                .map(source -> new ResponseEntity<>(TaskResourceFromEntityAssembler.toResourceFromEntity(source), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Operation(
            summary = "Get all tasks by milestone Id",
            description = "Retrieves all tasks associated with a specific milestone"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No tasks found for the milestone")
    })
    @GetMapping("/by-milestone-id/{milestoneId}")
    public ResponseEntity<List<TaskResource>> getAllTasksByMilestoneId(@PathVariable Long milestoneId){
        Optional<List<Task>> tasks = taskQueryService
                .handle(new GetAllTasksByMilestoneIdQuery(milestoneId));
        List<TaskResource> resources = tasks.map(source -> source.stream()
                        .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                        .toList())
                .orElseGet(List::of);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all tasks by milestone Id and person Id",
            description = "Retrieves all tasks associated with a specific milestone and person"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No tasks found for the milestone and person")
    })
    @GetMapping("/by-milestone-id/{milestoneId}/by-person-id/{personId}")
    public ResponseEntity<List<TaskResource>> getAllTasksByMilestoneIdAndPersonId(@PathVariable Long milestoneId, @PathVariable Long personId){
        Optional<List<Task>> tasks = taskQueryService
                .handle(new GetAllTasksByMilestoneIdAndPersonIdQuery(milestoneId, personId));
        List<TaskResource> resources = tasks.map(source -> source.stream()
                        .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                        .toList())
                .orElseGet(List::of);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a task with a given Id",
            description = "Delete a task based on the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("{taskId}")
    public ResponseEntity<GenericMessageResource> deleteTask(@PathVariable Long taskId){
        var deleteTaskCommand = new DeleteTaskCommand(taskId);
        taskCommandService.handle(deleteTaskCommand);
        return ResponseEntity.ok(new GenericMessageResource("Task successfully deleted"));
    }
}
