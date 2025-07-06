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
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.ProjectTeamMemberTypeRepository;
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

    private final ProjectTeamMemberTypeRepository projectTeamMemberTypeRepository;

    private final OrganizationContextFacade organizationContextFacade;

    /**
     * Constructs a {@link ProjectTeamMemberCommandServiceImpl} with required dependencies.
     *
     * @param projectTeamMemberRepository repository for persisting project team members
     * @param iamContextFacade IAM context facade for accessing user profile information
     * @param specialtyRepository repository for looking up specialty definitions
     */
    public ProjectTeamMemberCommandServiceImpl(ProjectTeamMemberRepository projectTeamMemberRepository,
                                               IAMContextFacade iamContextFacade,
                                               SpecialtyRepository specialtyRepository,
                                               ProjectTeamMemberTypeRepository projectTeamMemberTypeRepository,
                                               OrganizationContextFacade organizationContextFacade) {
        this.projectTeamMemberRepository = projectTeamMemberRepository;
        this.iamContextFacade = iamContextFacade;
        this.organizationContextFacade = organizationContextFacade;
        this.specialtyRepository = specialtyRepository;
        this.projectTeamMemberTypeRepository = projectTeamMemberTypeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProjectTeamMember> handle(CreateProjectTeamMemberCommand command) {
        var existingProjectTeamMember = projectTeamMemberRepository.findByOrganizationMemberIdAndProjectId(new OrganizationMemberId(command.organizationMemberId()), new ProjectId(command.projectId()));
        if (existingProjectTeamMember.isPresent()) {
            throw new IllegalArgumentException("Project team member already exists for organization member " + command.organizationMemberId() + " and project " + command.projectId());
        }
        var personId =
                organizationContextFacade.getPersonIdFromOrganizationMemberId(
                        command.organizationMemberId()
                );
        var projectTeamMember = new ProjectTeamMember(command);
        var personInformation = iamContextFacade.getProfileDetailsById(personId);
        if (personInformation == null) {
            throw new IllegalArgumentException("Person with ID " + personId + " not found");
        }
        var specialty = specialtyRepository.findByName(Specialties.valueOf(command.specialty()))
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found"));
        projectTeamMember.assignSpecialty(specialty);
        var type = projectTeamMemberTypeRepository.findByName(ProjectTeamMemberTypes.valueOf(command.memberType()))
                .orElseThrow(() -> new IllegalArgumentException("Project team member type not found"));
        projectTeamMember.assignTeamMemberType(type);
        projectTeamMember.setPersonalInformation(new PersonId(personId), personInformation.name(), personInformation.email());

        var createdTeamMember = projectTeamMemberRepository.save(projectTeamMember);
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

    /**
     * Retrieves a {@link ProjectTeamMemberType} by its name.
     *
     * @param name the name of the project team member type (as enum)
     * @return the corresponding {@link ProjectTeamMemberType} entity
     * @throws IllegalArgumentException if the type is not found
     */
    private ProjectTeamMemberType getProjectTeamMemberType(ProjectTeamMemberTypes name) {
        return this.projectTeamMemberTypeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Project team member type not found"));
    }

}

