package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.organizations.interfaces.acl.OrganizationContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateProjectTeamMemberCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectTeamMemberType;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.Specialty;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectTeamMemberTypes;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.Specialties;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.ProjectTeamMemberCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectTeamMemberRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.SpecialtyRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for handling commands related to {@link ProjectTeamMember} creation.
 *
 * <p>This class processes {@link CreateProjectTeamMemberCommand} instances, constructs the corresponding
 * domain entity, and persists it using the {@link ProjectTeamMemberRepository}.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class ProjectTeamMemberCommandServiceImpl implements ProjectTeamMemberCommandService {

    /** Repository interface for performing persistence operations on {@link ProjectTeamMember} entities. */
    private final ProjectTeamMemberRepository projectTeamMemberRepository;

    /** Facade to access identity and profile-related data from the IAM context. */
    private final IAMContextFacade iamContextFacade;

    /** Repository interface for retrieving {@link Specialty} domain entities. */
    private final SpecialtyRepository specialtyRepository;

    /**
     * Constructs a {@link ProjectTeamMemberCommandServiceImpl} with required dependencies.
     *
     * @param projectTeamMemberRepository repository for persisting project team members
     * @param iamContextFacade IAM context facade for accessing user profile information
     * @param organizationContextFacade unused in this implementation (can be removed)
     * @param specialtyRepository repository for looking up specialty definitions
     */
    public ProjectTeamMemberCommandServiceImpl(ProjectTeamMemberRepository projectTeamMemberRepository,
                                               IAMContextFacade iamContextFacade,
                                               OrganizationContextFacade organizationContextFacade,
                                               SpecialtyRepository specialtyRepository) {
        this.projectTeamMemberRepository = projectTeamMemberRepository;
        this.iamContextFacade = iamContextFacade;
        this.specialtyRepository = specialtyRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProjectTeamMember> handle(CreateProjectTeamMemberCommand command) {
        var teamMember = new ProjectTeamMember(command.organizationMemberId(), command.projectId());
        var createdTeamMember = projectTeamMemberRepository.save(teamMember);
        return Optional.of(createdTeamMember);
    }

    /**
     * Retrieves a {@link Specialty} by its name.
     *
     * @param name the name of the specialty (as enum)
     * @return the corresponding {@link Specialty} entity
     * @throws IllegalArgumentException if the specialty is not found
     */
    private Specialty getSpecialty(Specialties name) {
        return this.specialtyRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found"));
    }
}

