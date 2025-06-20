package com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.controllers;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.CreateProjectCommandFromResourceAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.assemblers.ProjectResourceFromEntityAssembler;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.CreateProjectResource;
import com.galaxiawonder.propgms.propgmsplatform.projects.interfaces.rest.resources.ProjectResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    ProjectController(ProjectCommandService projectCommandService) {
        this.projectCommandService = projectCommandService;
    }

    @PostMapping()
    public ResponseEntity<ProjectResource> createProject(@RequestBody CreateProjectResource resource) {
        Project project = projectCommandService.handle(
                CreateProjectCommandFromResourceAssembler.toCommandFromResource(resource)
        ).orElseThrow(() -> new RuntimeException("Error while creating the project"));

        ProjectResource response = ProjectResourceFromEntityAssembler.toResourceFromEntity(project);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
