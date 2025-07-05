package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.SeedProjectTeamMemberTypeCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectTeamMemberType;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectTeamMemberTypes;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectTeamMemberTypeCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectTeamMemberTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ProjectTeamMemberTypeCommandServiceImpl implements ProjectTeamMemberTypeCommandService {

    private final ProjectTeamMemberTypeRepository projectTeamMemberTypeRepository;

    public ProjectTeamMemberTypeCommandServiceImpl(ProjectTeamMemberTypeRepository projectTeamMemberTypeRepository) {
        this.projectTeamMemberTypeRepository = projectTeamMemberTypeRepository;
    }

    @Override
    public void handle(SeedProjectTeamMemberTypeCommand command) {
        Arrays.stream(ProjectTeamMemberTypes.values()).forEach(type -> {
            if (!projectTeamMemberTypeRepository.existsByName(type)) {
                projectTeamMemberTypeRepository.save(new ProjectTeamMemberType(type));
            }
        });
    }
}

